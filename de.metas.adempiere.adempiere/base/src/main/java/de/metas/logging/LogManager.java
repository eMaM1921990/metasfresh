package de.metas.logging;

import java.io.File;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.adempiere.util.Check;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.NullAutoCloseable;
import org.compiere.util.Ini;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import com.google.common.collect.ImmutableList;

import ch.qos.logback.classic.Level;

/**
 * Log Management.
 */
public final class LogManager
{
	public static Logger getLogger(final Class<?> clazz)
	{
		final Logger logger = LoggerFactory.getLogger(clazz);

		// Customize the logger if needed
		loggerCustomizer.customize(logger);

		return logger;
	}

	public static Logger getLogger(final String loggerName)
	{
		final Logger logger = LoggerFactory.getLogger(loggerName);

		// Customize the logger if needed
		loggerCustomizer.customize(logger);

		return logger;
	}

	private static final ILoggerCustomizer loggerCustomizer = SysConfigLoggerCustomizer.instance;

	/**
	 * Initialize Logging
	 *
	 * @param isClient client
	 */
	public static void initialize(final boolean isClient)
	{
		if (s_initialized.getAndSet(true))
		{
			return;
		}

		//
		// Forward all logs from JUL to SLF4J
		{
			SLF4JBridgeHandler.uninstall();
			SLF4JBridgeHandler.install();
		}

		setLevel(s_currentLevel);
	}

	private static final AtomicBoolean s_initialized = new AtomicBoolean(false);
	/** Current Log Level */
	private static Level s_currentLevel = Level.INFO;

	/** Logger */
	private static Logger log = LoggerFactory.getLogger(LogManager.class);

	public static boolean isFileLoggingEnabled()
	{
		return MetasfreshFileLoggerHelper.get().isDisabled();
	}

	public static void setFileLoggingEnabled(final boolean fileLoggingEnabled)
	{
		Ini.setProperty(Ini.P_TRACEFILE, fileLoggingEnabled);
		MetasfreshFileLoggerHelper.get().setDisabled(!fileLoggingEnabled);
	}

	public static List<Level> getAvailableLoggingLevels()
	{
		return LOGBACK_LEVELS;
	}

	public static void setLevelAndUpdateIni(final Level level)
	{
		if (level == null)
		{
			return;
		}

		Ini.setProperty(Ini.P_TRACELEVEL, level.toString());
		setLevel(level);
	}

	/**
	 * Set Level
	 *
	 * @param levelString string representation of level
	 */
	public static void setLevel(final String levelString)
	{
		final Level level = asLogbackLevel(levelString);
		if (level == null)
		{
			log.info("Ignored: " + levelString);
			return;
		}

		setLevel(level);
	}

	public static void setLevel(final Level level)
	{
		if (level == null)
		{
			return;
		}

		initialize(true);

		//
		if (level.toInt() == s_currentLevel.toInt())
		{
			return;
		}

		// JDBC if ALL
		setJDBCDebug(level.toInt() == Level.ALL_INT);

		//
		// SLF4J (logback)
		int countChangedLoggers = 0;
		final ILoggerFactory slf4j_Factory = LoggerFactory.getILoggerFactory();
		if (slf4j_Factory instanceof ch.qos.logback.classic.LoggerContext)
		{
			final ch.qos.logback.classic.LoggerContext logback_loggerContext = (ch.qos.logback.classic.LoggerContext)slf4j_Factory;
			for (final ch.qos.logback.classic.Logger logback_logger : logback_loggerContext.getLoggerList())
			{
				if (setLoggerLevel(logback_logger, level))
				{
					countChangedLoggers++;
				}
			}
		}

		System.out.println("Changing log level " + s_currentLevel + "->" + level + " (" + countChangedLoggers + " loggers affected)");

		s_currentLevel = level;
	}

	public static final boolean setLoggerLevel(final Logger logger, final Level level)
	{
		if (logger == null)
		{
			return false;
		}
		if (level == null)
		{
			return false;
		}
		if (!isOwnLogger(logger))
		{
			return false;
		}

		if (logger instanceof ch.qos.logback.classic.Logger)
		{
			((ch.qos.logback.classic.Logger)logger).setLevel(level);
			return true;
		}

		return false;
	}

	public static final boolean setLoggerLevel(final Logger logger, final String levelStr)
	{
		final Level level = asLogbackLevel(levelStr);
		return setLoggerLevel(logger, level);
	}

	public static String getLoggerLevelName(final Logger logger)
	{
		if (logger == null)
		{
			return null;
		}
		if (logger instanceof ch.qos.logback.classic.Logger)
		{
			final Level level = ((ch.qos.logback.classic.Logger)logger).getEffectiveLevel();
			return level == null ? null : level.toString();
		}

		return null;
	}

	private static final boolean isOwnLogger(final Logger logger)
	{
		final String loggerName = logger.getName();
		if (loggerName == null || loggerName.isEmpty())
		{
			return false;
		}

		for (final String loggerPrefix : OWNLOGGERS_NAME_PREFIXES)
		{
			if (loggerName.startsWith(loggerPrefix))
			{
				return true;
			}
		}

		return false;
	}

	private static final List<String> OWNLOGGERS_NAME_PREFIXES = ImmutableList.of(
			"de.metas", "org.adempiere", "org.compiere");

	/**
	 * Set JDBC Debug
	 *
	 * @param enable
	 */
	private static void setJDBCDebug(final boolean enable)
	{
		if (enable)
		{
			DriverManager.setLogWriter(new PrintWriter(System.err));
		}
		else
		{
			DriverManager.setLogWriter(null);
		}
	}	// setJDBCDebug

	public static final Level getLevel()
	{
		return s_currentLevel;
	}

	/**
	 * Is Logging Level logged
	 *
	 * @param level level
	 * @return true if it is logged
	 */
	public static boolean isLevel(final Level level)
	{
		if (level == null)
		{
			return false;
		}
		return level.toInt() >= s_currentLevel.toInt();
	}	// isLevel

	/**
	 * Is Logging Level FINEST logged
	 *
	 * @return true if it is logged
	 */
	public static boolean isLevelFinest()
	{
		return Level.TRACE_INT >= s_currentLevel.toInt();
	}	// isLevelFinest

	/**
	 * Is Logging Level FINER logged
	 *
	 * @return true if it is logged
	 */
	public static boolean isLevelFiner()
	{
		return isLevelFinest();
	}	// isLevelFiner

	/**
	 * Is Logging Level FINE logged
	 *
	 * @return true if it is logged
	 */
	public static boolean isLevelFine()
	{
		return Level.DEBUG_INT >= s_currentLevel.toInt();
	}

	/**
	 * Shutdown Logging system
	 */
	public static void shutdown()
	{
		// final LogManager mgr = LogManager.getLogManager();
		// mgr.reset();
	}	// shutdown

	public static final File getActiveLogFile()
	{
		return MetasfreshFileLoggerHelper.get().getActiveFileOrNull();
	}

	public static List<File> getLogFiles()
	{
		return MetasfreshFileLoggerHelper.get().getLogFiles();
	}

	public static boolean isActiveLogFile(final File file)
	{
		return MetasfreshFileLoggerHelper.get().isActiveLogFile(file);
	}

	public static void flushLogFile()
	{
		MetasfreshFileLoggerHelper.get().flushActiveLogFile();
	}

	public static final void rotateLogFile()
	{
		MetasfreshFileLoggerHelper.get().rotateLogFile();
	}

	public static IAutoCloseable temporaryDisableIssueReporting()
	{
		final MetasfreshIssueAppender issueAppender = MetasfreshIssueAppender.get();
		if (issueAppender == null)
		{
			return NullAutoCloseable.instance;
		}
		return issueAppender.temporaryDisableIssueReporting();
	}

	public static void skipIssueReportingForLoggerName(final Logger logger)
	{
		if (logger == null)
		{
			return;
		}
		skipIssueReportingForLoggerName(logger.getName());
	}

	public static void skipIssueReportingForLoggerName(final String loggerName)
	{
		final MetasfreshIssueAppender issueAppender = MetasfreshIssueAppender.get();
		if (issueAppender == null)
		{
			return;
		}
		issueAppender.skipIssueReportingForLoggerName(loggerName);
	}

	private static final List<ch.qos.logback.classic.Level> LOGBACK_LEVELS = ImmutableList.<ch.qos.logback.classic.Level> builder()
			.add(ch.qos.logback.classic.Level.OFF)
			.add(ch.qos.logback.classic.Level.ERROR)
			.add(ch.qos.logback.classic.Level.WARN)
			.add(ch.qos.logback.classic.Level.INFO)
			.add(ch.qos.logback.classic.Level.DEBUG)
			.add(ch.qos.logback.classic.Level.TRACE)
			.add(ch.qos.logback.classic.Level.ALL)
			.build();

	/**
	 * @return {@link Level} or null
	 */
	public static final ch.qos.logback.classic.Level asLogbackLevel(String levelStr)
	{
		if (Check.isEmpty(levelStr, true))
		{
			return null;
		}

		levelStr = levelStr.trim();

		// Try logback
		{
			final Level level = Level.toLevel(levelStr, null);
			if (level != null)
			{
				return level;
			}
		}

		// Try JUL
		try
		{
			final java.util.logging.Level julLevel = java.util.logging.Level.parse(levelStr);
			final Level level = asLogbackLevel(julLevel);
			if (level != null)
			{
				return level;
			}
		}
		catch (final Exception e)
		{
			//
		}

		return null;
	}

	private static final ch.qos.logback.classic.Level asLogbackLevel(final java.util.logging.Level julLevel)
	{
		if (julLevel == null)
		{
			return null;
		}

		final int julLevelValue = julLevel.intValue();
		if (julLevelValue <= JUL_TRACE_LEVEL_THRESHOLD)
		{
			return ch.qos.logback.classic.Level.TRACE;
		}
		else if (julLevelValue <= JUL_DEBUG_LEVEL_THRESHOLD)
		{
			return ch.qos.logback.classic.Level.DEBUG;
		}
		else if (julLevelValue <= JUL_INFO_LEVEL_THRESHOLD)
		{
			return ch.qos.logback.classic.Level.INFO;
		}
		else if (julLevelValue <= JUL_WARN_LEVEL_THRESHOLD)
		{
			return ch.qos.logback.classic.Level.WARN;
		}
		else
		{
			return ch.qos.logback.classic.Level.ERROR;
		}
	}

	private static final int JUL_TRACE_LEVEL_THRESHOLD = java.util.logging.Level.FINEST.intValue();
	private static final int JUL_DEBUG_LEVEL_THRESHOLD = java.util.logging.Level.FINE.intValue();
	private static final int JUL_INFO_LEVEL_THRESHOLD = java.util.logging.Level.INFO.intValue();
	private static final int JUL_WARN_LEVEL_THRESHOLD = java.util.logging.Level.WARNING.intValue();

	public static void updateConfigurationFromIni()
	{
		final String logLevelStr = Ini.getProperty(Ini.P_TRACELEVEL);
		final Level level = asLogbackLevel(logLevelStr);
		setLevel(level);

		if (Ini.isClient())
		{
			final boolean fileLoggingEnabled = Ini.isPropertyBool(Ini.P_TRACEFILE);
			MetasfreshFileLoggerHelper.get().setDisabled(!fileLoggingEnabled);
		}
	}

	private LogManager()
	{
		super();
	}
}
