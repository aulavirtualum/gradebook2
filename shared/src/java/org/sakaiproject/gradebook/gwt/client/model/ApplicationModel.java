/**********************************************************************************
*
* $Id:$
*
***********************************************************************************
*
* Copyright (c) 2008, 2009 The Regents of the University of California
*
* Licensed under the
* Educational Community License, Version 2.0 (the "License"); you may
* not use this file except in compliance with the License. You may
* obtain a copy of the License at
* 
* http://www.osedu.org/licenses/ECL-2.0
* 
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an "AS IS"
* BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
* or implied. See the License for the specific language governing
* permissions and limitations under the License.
*
**********************************************************************************/
package org.sakaiproject.gradebook.gwt.client.model;

import java.util.List;

import org.sakaiproject.gradebook.gwt.client.model.GradebookModel.GradeType;

public class ApplicationModel extends EntityModel {

	private static final long serialVersionUID = 1L;
	
	private List<GradebookModel> gradebookModels;
	private String placementId;
	private String userName;
	private String helpUrl;
	private List<GradeType> enabledGradeTypes;
		
	public ApplicationModel() {
		
	}
	
	public List<GradebookModel> getGradebookModels() {
		return gradebookModels;
	}

	public void setGradebookModels(List<GradebookModel> gradebookModels) {
		this.gradebookModels = gradebookModels;
	}
	
	public String getPlacementId() {
		return placementId;
	}

	public void setPlacementId(String placementId) {
		this.placementId = placementId;
	}

	@Override
	public String getDisplayName() {
		
		return "Gradebook Tool";
	}

	@Override
	public String getIdentifier() {
		
		return getPlacementId();
	}

	public String getHelpUrl() {
		return helpUrl;
	}

	public void setHelpUrl(String helpUrl) {
		this.helpUrl = helpUrl;
	}

	public List<GradeType> getEnabledGradeTypes() {
		return enabledGradeTypes;
	}

	public void setEnabledGradeTypes(List<GradeType> enabledGradeTypes) {
		this.enabledGradeTypes = enabledGradeTypes;
	}
	
}
