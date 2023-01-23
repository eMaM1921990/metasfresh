/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2022 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.lims.process;

import de.metas.lims.process.model.ResultModel;
import de.metas.lims.process.service.LIMSService;
import de.metas.ui.web.config.WebConfig;
import lombok.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(LIMSCallBackRestController.ENDPOINT)
@Validated
public class LIMSCallBackRestController
{
	static final String ENDPOINT = WebConfig.ENDPOINT_ROOT + "/boms/sharing";
	private LIMSService LIMSService;


	public LIMSCallBackRestController(@NonNull final LIMSService LIMSService) {
		this.LIMSService = LIMSService;
	}

	@PostMapping(params = {"bomCode"})
	public void updateBOM(@RequestParam String bomCode, @RequestBody List< @Valid ResultModel> resultModel) {
		LIMSService.updateResult(bomCode, resultModel);
	}


}
