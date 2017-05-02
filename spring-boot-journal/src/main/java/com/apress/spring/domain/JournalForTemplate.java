package com.apress.spring.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class JournalForTemplate {
	
	
	private Long id;

	private String title;

	private Date created;

	private String summary;

	private SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

	public JournalForTemplate() {
	}

	public JournalForTemplate(Long id, String title, String summary, Date date) {
		this.id = id;
		this.title = title;
		this.summary = summary;
		this.created = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCreatedAsShort() {
		return format.format(created);
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JournalEntry(");
		builder.append("Id=");
		builder.append(id);
		builder.append(", Title=");
		builder.append(title);
		builder.append(", Summary=");
		builder.append(summary);
		builder.append(", Created=");
		builder.append(format.format(created));
		builder.append(")");
		return builder.toString();
	}

}
