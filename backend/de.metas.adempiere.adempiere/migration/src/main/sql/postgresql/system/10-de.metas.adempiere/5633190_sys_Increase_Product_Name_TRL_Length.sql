-- 2022-04-04T12:33:02.081Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=510,Updated=TO_TIMESTAMP('2022-04-04 15:33:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3330
;

-- 2022-04-04T12:33:03.192Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_product_trl','Name','VARCHAR(80)',null,null)
;

