package com.crudydot.messenger.plugin.ui.views;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.crudydot.messenger.plugin.data.TreeNode;

public class TreeContentProvider implements IStructuredContentProvider,
		ITreeContentProvider {

	private TreeNode invisibleRoot;
	private TreeNode usersNode;
	
	public TreeContentProvider(){
		
	}
	
	@Override
	public void dispose() {
		if(invisibleRoot != null){
			invisibleRoot.dispose();
		}
		if(usersNode != null){
			usersNode.dispose();
			usersNode = null;
		}
	}

	public void init(){
		invisibleRoot = new TreeNode(0);
		
		TreeNode forumNode = new TreeNode(TreeNode.TYPE_CATORY_FORUM);
		forumNode.setLabel("Forums - 1");
		invisibleRoot.addChild(forumNode);
		
		TreeNode forumTLNode = new TreeNode(TreeNode.TYPE_NODE_FORUM);
		forumTLNode.setLabel("Tech Lead - 1");
		forumNode.addChild(forumTLNode);
		
		this.usersNode = new TreeNode(TreeNode.TYPE_CATORY_USERS);
		usersNode.setLabel("Online Users -1 ");
		invisibleRoot.addChild(usersNode);
		
		TreeNode rd = new TreeNode(TreeNode.TYPE_NODE_USER);
		rd.setData("Rudy.Deng - 1");
		usersNode.addChild(rd);
	}
	
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if(MessengerView.class.getName().equals(viewer.getClass().getName())){
			String userId = (String) newInput;
			
			TreeNode newUserNode = new TreeNode(TreeNode.TYPE_NODE_USER);
			newUserNode.setData(userId);
			
			this.usersNode.addChild(newUserNode);
		}
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		TreeNode p = (TreeNode)parentElement;
		
		List<TreeNode> children = p.getChildren();
		
		return children == null ? null : children.toArray();
	}

	@Override
	public Object getParent(Object element) {
		return element == null ? null : ((TreeNode)element).getParent();
	}

	@Override
	public boolean hasChildren(Object element) {
		return ((TreeNode)element).hasChildren();
	}

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof TreeNode) {
			return getChildren(inputElement);
		}else{
			if (invisibleRoot==null) init();
			return getChildren(invisibleRoot);
		}
	}

}
