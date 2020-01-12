package com.fossilia.visuafiles.group;

public class ExtensionGroup extends Group {
	private String desc;

	public ExtensionGroup(){
		super();
	}

	public ExtensionGroup(String name){
		super(name);
	}

	public void setDesc(String desc){
		this.desc = desc;
	}

	public String getDesc(){
		return desc;
	}
}