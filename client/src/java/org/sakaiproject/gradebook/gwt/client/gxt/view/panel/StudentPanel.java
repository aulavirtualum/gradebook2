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
package org.sakaiproject.gradebook.gwt.client.gxt.view.panel;

import java.util.Collections;
import java.util.List;

import org.sakaiproject.gradebook.gwt.client.AppConstants;
import org.sakaiproject.gradebook.gwt.client.DataTypeConversionUtil;
import org.sakaiproject.gradebook.gwt.client.I18nConstants;
import org.sakaiproject.gradebook.gwt.client.model.GradebookModel;
import org.sakaiproject.gradebook.gwt.client.model.ItemModel;
import org.sakaiproject.gradebook.gwt.client.model.StatisticsModel;
import org.sakaiproject.gradebook.gwt.client.model.StudentModel;
import org.sakaiproject.gradebook.gwt.client.model.GradebookModel.CategoryType;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;

public class StudentPanel extends ContentPanel {

	private static final int COL_ANAM = 0; 
	private static final int COL_GRADE = 1; 
	private static final int COL_MEAN = 2; 
	private static final int COL_STDV = 3; 
	private static final int COL_MEDI = 4; 
	private static final int COL_MODE = 5; 
	private static final int COL_RANK = 6; 
	private static final int COL_COMM = 7; 

	
	private TextField<String> defaultTextField= new TextField<String>();
	private TextArea defaultTextArea = new TextArea();
	private NumberFormat defaultNumberFormat = NumberFormat.getFormat("#.###");
	private NumberField defaultNumberField = new NumberField();
    private FlexTable studentInformation, gradeInformation;
    private ContentPanel studentInformationPanel, gradeInformationPanel;

	private StudentModel learnerGradeRecordCollection;
	
	private boolean isStudentView;
	
	private boolean displayRank; 
	
	private GradebookModel selectedGradebook;
	
	public StudentPanel(boolean isStudentView, boolean displayRank) {
		this.isStudentView = isStudentView;
		this.defaultNumberField.setFormat(defaultNumberFormat);
		this.defaultNumberField.setSelectOnFocus(true);
		this.defaultNumberField.addInputStyleName("gbNumericFieldInput");
		this.defaultTextArea.addInputStyleName("gbTextAreaInput");
		this.defaultTextField.addInputStyleName("gbTextFieldInput");
		this.displayRank = displayRank;
		setFrame(true);
		setHeaderVisible(false);
		setLayout(new RowLayout());

		studentInformation = new FlexTable(); 
		studentInformation.setStyleName("gbStudentInformation");
		studentInformationPanel = new ContentPanel();
		studentInformationPanel.setBorders(true);
		studentInformationPanel.setFrame(true);
		studentInformationPanel.setHeaderVisible(false);
		studentInformationPanel.setHeading("Individual Grade Summary");
		studentInformationPanel.setLayout(new FitLayout());
		studentInformationPanel.setScrollMode(Scroll.AUTO);
		studentInformationPanel.add(studentInformation);
		add(studentInformationPanel, new RowData(-1, -1, new Margins(5, 0, 0, 0)));

		gradeInformation = new FlexTable();
		gradeInformation.setStyleName("gbStudentInformation");
		gradeInformationPanel = new ContentPanel();
		gradeInformationPanel.setBorders(true);
		gradeInformationPanel.setFrame(true);
		gradeInformationPanel.setHeaderVisible(false);
		gradeInformationPanel.setLayout(new FlowLayout());
		gradeInformationPanel.setScrollMode(Scroll.AUTO);
		gradeInformationPanel.add(gradeInformation);
		add(gradeInformationPanel, new RowData(1, 1, new Margins(5, 0, 0, 0)));
		
	}
	
	public StudentModel getStudentRow() {
		return learnerGradeRecordCollection;
	}
	
	public void onRefreshGradebookSetup(GradebookModel selectedGradebook) {
		this.selectedGradebook = selectedGradebook;
		
		updateCourseGrade(learnerGradeRecordCollection.getStudentGrade());
		
		setStudentInfoTable();
		
		setGradeInfoTable(selectedGradebook, learnerGradeRecordCollection);
	}
	
	public void onChangeModel(GradebookModel selectedGradebook, StudentModel learnerGradeRecordCollection) {
		if (learnerGradeRecordCollection != null) {
			this.selectedGradebook = selectedGradebook;
			this.learnerGradeRecordCollection = learnerGradeRecordCollection;
			
			updateCourseGrade(learnerGradeRecordCollection.getStudentGrade());
			
			setStudentInfoTable();
			
			setGradeInfoTable(selectedGradebook, learnerGradeRecordCollection);
		}
	}
	
	public void onItemUpdated(ItemModel itemModel) {

	}
	
	protected void onRender(Element parent, int pos) {
		super.onRender(parent, pos);

	}
	
	public void onResize(int x, int y) {
		super.onResize(x, y);
		

	}
	
	public void refreshColumns() {

	}
	
	public void refreshData() {

	}

	
	private void updateCourseGrade(String newGrade)
	{
		if (!isStudentView || (selectedGradebook != null && selectedGradebook.getGradebookItemModel() != null 
				&& DataTypeConversionUtil.checkBoolean(selectedGradebook.getGradebookItemModel().getReleaseGrades()))) {
			// To force a refresh, let's first hide the owning panel
			studentInformationPanel.hide();
			studentInformation.setText(6, 1, newGrade);
			studentInformationPanel.show();
		} else {
			studentInformationPanel.hide();
			studentInformation.setText(6, 0, "");
			studentInformation.setText(6, 1, "");
			studentInformationPanel.show();
		}
		
		if (learnerGradeRecordCollection != null)
			learnerGradeRecordCollection.set(StudentModel.Key.COURSE_GRADE.name(), newGrade);
	}
	
	// FIXME - i18n 
	// FIXME - need to assess impact of doing it this way... 
	
	private void setStudentInfoTable() {		
		// To force a refresh, let's first hide the owning panel
		studentInformationPanel.hide();
	
		// Now, let's update the student information table
		FlexCellFormatter formatter = studentInformation.getFlexCellFormatter();
		
        studentInformation.setText(1, 0, "Name");
        formatter.setStyleName(1, 0, "gbImpact");
        studentInformation.setText(1, 1, learnerGradeRecordCollection.getStudentName());

        studentInformation.setText(2, 0, "Email");
        formatter.setStyleName(2, 0, "gbImpact");
        studentInformation.setText(2, 1, learnerGradeRecordCollection.getStudentEmail());

        studentInformation.setText(3, 0, "ID");
        formatter.setStyleName(3, 0, "gbImpact");
        studentInformation.setText(3, 1, learnerGradeRecordCollection.getStudentDisplayId());

        studentInformation.setText(4, 0, "Section");
        formatter.setStyleName(4, 0, "gbImpact");
        studentInformation.setText(4, 1, learnerGradeRecordCollection.getStudentSections());
    
        studentInformation.setText(5, 0, "");
        formatter.setColSpan(5, 0, 2);
        
        if (!isStudentView || (DataTypeConversionUtil.checkBoolean(selectedGradebook.getGradebookItemModel().getReleaseGrades()))) {
	        studentInformation.setText(6, 0, "Course Grade");
	        formatter.setStyleName(6, 0, "gbImpact");
	        studentInformation.setText(6, 1, learnerGradeRecordCollection.getStudentGrade());
        }
        studentInformationPanel.show();
	}

	
	private void populateGradeInfoRow(int row, ItemModel item, StudentModel learner, FlexCellFormatter formatter, StatisticsModel stats) {
		String itemId = item.getIdentifier();
		Object value = learner.get(itemId);
		String commentFlag = new StringBuilder().append(itemId).append(StudentModel.COMMENT_TEXT_FLAG).toString();
		String comment = learner.get(commentFlag);
		String excusedFlag = new StringBuilder().append(itemId).append(StudentModel.DROP_FLAG).toString();
		
		String mean = (stats == null ? "" : stats.getMean());
		String stdDev = (stats == null ? "" : stats.getStandardDeviation()); 
		String median = (stats == null ? "" : stats.getMedian());
		String mode = (stats == null ? "" : stats.getMode()); 
		String rank = (stats == null ? "" : stats.getRank());
		
		
		boolean isExcused = DataTypeConversionUtil.checkBoolean((Boolean)learner.get(excusedFlag));
		boolean isIncluded = DataTypeConversionUtil.checkBoolean((Boolean)item.getIncluded());
		
		gradeInformation.setText(row, 0, item.getName());
        formatter.setStyleName(row, 0, "gbRecordLabel");
        formatter.setVerticalAlignment(row, 0, HasVerticalAlignment.ALIGN_TOP);
        
        StringBuilder resultBuilder = new StringBuilder();
        if (value == null)
        	resultBuilder.append("Ungraded");
        else {
        	
        	switch (selectedGradebook.getGradebookItemModel().getGradeType()) {
        	case POINTS:
        		resultBuilder.append(NumberFormat.getDecimalFormat().format(((Double)value).doubleValue()));
        		
        		if (item.getPoints() != null)
        			resultBuilder.append(" out of ").append(item.getPoints()).append(" points");
        		
        		break;
        	case PERCENTAGES:
        		resultBuilder.append(NumberFormat.getDecimalFormat().format(((Double)value).doubleValue()))
        			.append("%");
        		
        		break;
        	case LETTERS:
        		resultBuilder.append(value);
        		break;
        	}
        	
        	
        }
        
        if (!isIncluded) 
        	resultBuilder.append(" (not included in grade)");
        
        if (isExcused)
        	resultBuilder.append(" (excused)");
        
        gradeInformation.setText(row, COL_GRADE, resultBuilder.toString());
        formatter.setStyleName(row, COL_GRADE, "gbRecordFieldStudentGrades");
        // Mean
        gradeInformation.setText(row, COL_MEAN, mean);
        formatter.setStyleName(row, COL_MEAN, "gbRecordFieldStudentGrades");
        // Std Dev
        gradeInformation.setText(row, COL_STDV, stdDev);
        formatter.setStyleName(row, COL_STDV, "gbRecordFieldStudentGrades");
        // Median 
        gradeInformation.setText(row, COL_MEDI, median);
        formatter.setStyleName(row, COL_MEDI, "gbRecordFieldStudentGrades");

        // Mode
        gradeInformation.setText(row, COL_MODE, mode);
        formatter.setStyleName(row, COL_MODE, "gbRecordFieldStudentGrades");

        if (displayRank)
        {
        	// Rank 
        	gradeInformation.setText(row, COL_RANK, rank);
        	formatter.setStyleName(row, COL_RANK, "gbRecordFieldStudentGrades");
        }
        // Comment
        gradeInformation.setText(row, COL_COMM, comment);
        formatter.setStyleName(row, COL_COMM, "gbRecordFieldStudentGrades");
        
	}
	
	// So for stats, we'll have the following columns: 
	// grade | Mean | Std Deviation | Median | Mode | Comment 

	private void setGradeInfoTable(GradebookModel selectedGradebook, StudentModel learner) {		
		// To force a refresh, let's first hide the owning panel
		gradeInformationPanel.hide();
	
		// Now, let's update the student information table
		FlexCellFormatter formatter = gradeInformation.getFlexCellFormatter();
		
		// Start by removing all existing rows, since we may be reducing the visibility
		for (int i=gradeInformation.getRowCount() - 1;i>=0;i--) {
			for (int j=0;j<gradeInformation.getCellCount(i);j++) {
				gradeInformation.removeCell(i, j);
			}
			gradeInformation.removeRow(i);
		}
		
		ItemModel gradebookItemModel = selectedGradebook.getGradebookItemModel();
		List<StatisticsModel> statsList = selectedGradebook.getStatsModel(); 
		
		
		
		boolean isDisplayReleasedItems = DataTypeConversionUtil.checkBoolean(gradebookItemModel.getReleaseItems());
		boolean columnsDisplayed = false; 
		if (isDisplayReleasedItems) {
			boolean isNothingToDisplay = true;
			int row=0;
			if (gradebookItemModel.getChildCount() > 0) {
				for (ItemModel child : gradebookItemModel.getChildren()) {
					switch (child.getItemType()) {
					case CATEGORY:
						boolean isCategoryHeaderDisplayed = false;
						
						for (ItemModel item : child.getChildren()) {
							if (DataTypeConversionUtil.checkBoolean(item.getReleased())) {
								
								if (!isCategoryHeaderDisplayed) {
									if (selectedGradebook.getGradebookItemModel().getCategoryType() != CategoryType.NO_CATEGORIES) {
										if (!columnsDisplayed)
										{
											displayColumnHeaders(row, formatter);
											row++; 
											columnsDisplayed = true; 
										}
										gradeInformation.setText(row, 0, child.getName());
								        formatter.setStyleName(row, 0, "gbHeader");
								        formatter.setColSpan(row, 0, 3);
								        row++;
									}
									isCategoryHeaderDisplayed = true;
								}
								if (!columnsDisplayed)
								{
									displayColumnHeaders(row, formatter);
									row++; 
									columnsDisplayed = true; 
								}
								StatisticsModel stats = null; 
								stats = getStatsModelForItem(item.getIdentifier(), statsList); 

								populateGradeInfoRow(row, item, learner, formatter, stats);
								isNothingToDisplay = false;
								row++;
							} 
						}
						break;
					case ITEM:
						StatisticsModel stats = null; 
						stats = getStatsModelForItem(child.getIdentifier(), statsList); 

						if (DataTypeConversionUtil.checkBoolean(child.getReleased())) {
							if (!columnsDisplayed)
							{
								displayColumnHeaders(row, formatter);
								row++; 
								columnsDisplayed = true; 
							}
							populateGradeInfoRow(row, child, learner, formatter, stats);
							isNothingToDisplay = false;
							row++;
						}
						break;
					}
				}
			}
			
			if (isNothingToDisplay) {
				I18nConstants i18n = Registry.get(AppConstants.I18N);
				gradeInformation.setText(0, 0, i18n.notifyNoReleasedItems());
			}
			
		} else {
			I18nConstants i18n = Registry.get(AppConstants.I18N);
			gradeInformation.setText(0, 0, i18n.notifyNotDisplayingReleasedItems());
		}

        gradeInformationPanel.show();
	}
	
	private StatisticsModel getStatsModelForItem(String id,
			List<StatisticsModel> statsList) {
		int idx = -1; 
		
		StatisticsModel key = new StatisticsModel();
		key.setAssignmentId(id); 
		//Window.alert("id: " + (id == null ? "null" : "not null") + " \nitemId: " + id + "\n idx: " + idx); 
		idx = Collections.binarySearch(statsList, key);
		
		if (idx >= 0)
		{
			return statsList.get(idx); 
		}
		
		return null;
	}


	private void displayColumnHeaders(int row, FlexCellFormatter formatter) {
		int col = 1; 
	
		gradeInformation.setText(row, COL_GRADE, ""); 
		formatter.setStyleName(row, COL_GRADE, "gbHeaderStudentGrades");
		
		gradeInformation.setText(row, COL_MEAN, "Mean"); 
		formatter.setStyleName(row, COL_MEAN, "gbHeaderStudentGrades");

		gradeInformation.setHTML(row, COL_STDV, "Standard<BR>Deviation"); 
		formatter.setStyleName(row, COL_STDV, "gbHeaderStudentGrades");

		gradeInformation.setText(row, COL_MEDI, "Median"); 
		formatter.setStyleName(row, COL_MEDI, "gbHeaderStudentGrades");

		gradeInformation.setText(row, COL_MODE, "Mode"); 
		formatter.setStyleName(row, COL_MODE, "gbHeaderStudentGrades");

		if (displayRank)
		{
			gradeInformation.setText(row, COL_RANK, "Rank"); 
			formatter.setStyleName(row, COL_RANK, "gbHeaderStudentGrades");
		}
		gradeInformation.setText(row, COL_COMM, "Comment"); 
		formatter.setStyleName(row, COL_COMM, "gbHeaderStudentGrades");

		
		
	}

	public boolean isStudentView() {
		return isStudentView;
	}

	public void setStudentView(boolean isStudentView) {
		this.isStudentView = isStudentView;
	}

	public StudentModel getStudentModel() {
		return learnerGradeRecordCollection;
	}
}
