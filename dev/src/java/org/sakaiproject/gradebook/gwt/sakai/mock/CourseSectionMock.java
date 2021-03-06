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
package org.sakaiproject.gradebook.gwt.sakai.mock;

import java.util.List;

import org.sakaiproject.section.api.coursemanagement.Course;
import org.sakaiproject.section.api.coursemanagement.CourseSection;
import org.sakaiproject.section.api.coursemanagement.Meeting;

public class CourseSectionMock implements CourseSection {

	private String uuid;
	private String title;
	private String eid =  null;
	
	public CourseSectionMock(String uuid, String title) {
		this(uuid, title, null);
	}
	
	public CourseSectionMock(String uuid, String title, String eid) {
		this.uuid = uuid;
		this.title = title;
		this.eid = eid;
	}
	
	public String getCategory() {
		// TODO Auto-generated method stub
		return null;
	}

	public Course getCourse() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getEid() {
		return eid;
	}

	public Integer getMaxEnrollments() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Meeting> getMeetings() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getTitle() {
		return title;
	}

	public String getUuid() {
		return uuid;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
