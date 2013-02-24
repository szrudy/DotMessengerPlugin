package com.crudydot.messenger.plugin.data;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;

public class TreeNode implements IAdaptable {

	public static final int TYPE_CATORY_FORUM = 100;
	public static final int TYPE_CATORY_USERS = 200;
	public static final int TYPE_NODE_FORUM = 10;
	public static final int TYPE_NODE_USER = 20;

	private int type;
	private String label;
	private List<TreeNode> children;
	private Object data;
	private TreeNode parent;

	public TreeNode(int type) {
		this.type = type;
	}

	public TreeNode getParent() {
		return this.parent;
	}

	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	public int getType() {
		return this.type;
	}

	public Object getData() {
		return this.data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public boolean hasChildren() {
		return children != null && children.size() > 0;
	}

	public List<TreeNode> getChildren() {
		return this.children;
	}

	public void addChild(TreeNode child) {
		if (children == null) {
			this.children = new ArrayList<TreeNode>();
		}
		children.add(child);
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public void dispose(){
		if(this.children != null){
			for(TreeNode node : this.children){
				if(node != null){
					node.dispose();
					node.setParent(null);
					node.setData(null);
				}
			}
			this.children = null;
			this.label = null;
		}
	}

	@Override
	public Object getAdapter(Class adapter) {

		return null;
	}
}
