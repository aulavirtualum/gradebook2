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

import java.util.ArrayList;
import java.util.List;

import org.sakaiproject.gradebook.gwt.client.AppConstants;
import org.sakaiproject.gradebook.gwt.client.ConfigUtil;
import org.sakaiproject.gradebook.gwt.client.DataTypeConversionUtil;

import com.extjs.gxt.ui.client.data.BaseModel;

public class ConfigurationModel extends BaseModel implements Configuration {

	private static final long serialVersionUID = 1L;
	
	public ConfigurationModel() {
		
	}
	
	public ConfigurationModel(Long gradebookId) {
		setGradebookId(gradebookId);
	}
	
	/* (non-Javadoc)
	 * @see org.sakaiproject.gradebook.gwt.client.model.Configuration#getGradebookId()
	 */
	public Long getGradebookId() {
		return get(ConfigurationKey.GRADEBOOKID.name());
	}

	/* (non-Javadoc)
	 * @see org.sakaiproject.gradebook.gwt.client.model.Configuration#setGradebookId(java.lang.Long)
	 */
	public void setGradebookId(Long gradebookId) {
		set(ConfigurationKey.GRADEBOOKID.name(), gradebookId);
	}

	/* (non-Javadoc)
	 * @see org.sakaiproject.gradebook.gwt.client.model.Configuration#getUserUid()
	 */
	public String getUserUid() {
		return get(ConfigurationKey.USERUID.name());
	}

	/* (non-Javadoc)
	 * @see org.sakaiproject.gradebook.gwt.client.model.Configuration#setUserUid(java.lang.String)
	 */
	public void setUserUid(String userUid) {
		set(ConfigurationKey.USERUID.name(), userUid);
	}

	/* (non-Javadoc)
	 * @see org.sakaiproject.gradebook.gwt.client.model.Configuration#isAscending(java.lang.String)
	 */
	public boolean isAscending(String gridId) {
		String isAsc = get(ConfigUtil.getAscendingId(gridId));
		
		return DataTypeConversionUtil.checkBoolean(Boolean.valueOf(isAsc));
	}
	
	/* (non-Javadoc)
	 * @see org.sakaiproject.gradebook.gwt.client.model.Configuration#isColumnHidden(java.lang.String, java.lang.String)
	 */
	public boolean isColumnHidden(String gridId, String columnId) {
		return isColumnHidden(gridId, columnId, true);
	}
	
	/* (non-Javadoc)
	 * @see org.sakaiproject.gradebook.gwt.client.model.Configuration#isColumnHidden(java.lang.String, java.lang.String, boolean)
	 */
	public boolean isColumnHidden(String gridId, String columnId, boolean valueForNull) {
		String hidden = get(ConfigUtil.getColumnHiddenId(gridId, columnId));
		
		if (hidden == null)
			return valueForNull;
		
		return Boolean.valueOf(hidden).booleanValue();
	}
	
	/* (non-Javadoc)
	 * @see org.sakaiproject.gradebook.gwt.client.model.Configuration#getColumnWidth(java.lang.String, java.lang.String, java.lang.String)
	 */
	public int getColumnWidth(String gridId, String columnId, String name) {
		String columnWidth = get(ConfigUtil.getColumnWidthId(gridId, columnId));
		
		int cw = 200;
			
		if (columnWidth != null)
			cw = Integer.parseInt(columnWidth);
		else {
			if (name != null) {
				cw = name.length() * 7;
			}
			
			if (cw < 100)
				cw = 100;
		}
			
		return cw;
	}
	
	/* (non-Javadoc)
	 * @see org.sakaiproject.gradebook.gwt.client.model.Configuration#getPageSize(java.lang.String)
	 */
	public int getPageSize(String gridId) {
		String pageSize = get(ConfigUtil.getPageSizeId(gridId));
		
		if (pageSize == null)
			return -1;
		
		return Integer.parseInt(pageSize);
	}
	
	/* (non-Javadoc)
	 * @see org.sakaiproject.gradebook.gwt.client.model.Configuration#setPageSize(java.lang.String, java.lang.Integer)
	 */
	public void setPageSize(String gridId, Integer pageSize) {
		set(ConfigUtil.getPageSizeId(gridId), String.valueOf(pageSize));
	}
	
	/* (non-Javadoc)
	 * @see org.sakaiproject.gradebook.gwt.client.model.Configuration#getSelectedMultigradeColumns()
	 */
	public List<String> getSelectedMultigradeColumns() {
		String value = get(AppConstants.SELECTED_COLUMNS);
		
		List<String> columnIds = new ArrayList<String>();
		
		if (value != null) {
			String[] tokens = value.split(":");
			for (int i=0;i<tokens.length;i++) {
				columnIds.add(tokens[i]);
			}
		}
		
		return columnIds;
	}
	
	/* (non-Javadoc)
	 * @see org.sakaiproject.gradebook.gwt.client.model.Configuration#getSortField(java.lang.String)
	 */
	public String getSortField(String gridId) {
		return get(ConfigUtil.getSortFieldId(gridId));
	}
	
	
	/* (non-Javadoc)
	 * @see org.sakaiproject.gradebook.gwt.client.model.Configuration#setColumnHidden(java.lang.String, java.lang.String, java.lang.Boolean)
	 */
	public void setColumnHidden(String gridId, String columnId, Boolean isHidden) {
		set(ConfigUtil.getColumnHiddenId(gridId, columnId), String.valueOf(isHidden));
	}
	
	/* (non-Javadoc)
	 * @see org.sakaiproject.gradebook.gwt.client.model.Configuration#setColumnWidth(java.lang.String, java.lang.String, java.lang.Integer)
	 */
	public void setColumnWidth(String gridId, String columnId, Integer width) {
		set(ConfigUtil.getColumnWidthId(gridId, columnId), String.valueOf(width));
	}
	
	/* (non-Javadoc)
	 * @see org.sakaiproject.gradebook.gwt.client.model.Configuration#setSortDirection(java.lang.String, java.lang.Boolean)
	 */
	public void setSortDirection(String gridId, Boolean isAscending) {
		set(ConfigUtil.getAscendingId(gridId), String.valueOf(isAscending));
	}
	
	/* (non-Javadoc)
	 * @see org.sakaiproject.gradebook.gwt.client.model.Configuration#setSortField(java.lang.String, java.lang.String)
	 */
	public void setSortField(String gridId, String sortField) {
		set(ConfigUtil.getSortFieldId(gridId), sortField);
	}
	
}
