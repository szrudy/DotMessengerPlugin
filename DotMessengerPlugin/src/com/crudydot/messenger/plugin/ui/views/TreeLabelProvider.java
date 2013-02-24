package com.crudydot.messenger.plugin.ui.views;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.crudydot.messenger.plugin.DotMessengerPlugin;
import com.crudydot.messenger.plugin.data.TreeNode;

public class TreeLabelProvider extends LabelProvider {

	private static final String IMG_PATH_CATEGORY_FORUM = "icons/chat_category_forum.ico";
	private static final String IMG_PATH_CATEGORY_USERS = "icons/chat_category_user.ico";
	private static final String IMG_PATH_NODE_FORUM = "icons/chat_node_forum.ico";
	private static final String IMG_PATH_NODE_USER = "icons/chat_node_user.ico";
	@Override
	public Image getImage(Object element) {
		TreeNode tn = (TreeNode)element;
		ImageDescriptor imgDsptr = null;
		if(tn != null ){
			switch(tn.getType()){
			case TreeNode.TYPE_CATORY_FORUM:
				imgDsptr = getImageDescriptor(IMG_PATH_CATEGORY_FORUM);
				break;
			case TreeNode.TYPE_CATORY_USERS:
				imgDsptr = getImageDescriptor(IMG_PATH_CATEGORY_USERS);
				break;
			case TreeNode.TYPE_NODE_FORUM:
				imgDsptr = getImageDescriptor(IMG_PATH_NODE_FORUM);
				break;
				
			case TreeNode.TYPE_NODE_USER:
				imgDsptr = getImageDescriptor(IMG_PATH_NODE_USER);
				break;
			
			}
		}
		
		return imgDsptr == null ? super.getImage(element) : imgDsptr.createImage();
	}

	@Override
	public String getText(Object element) {
		String text = "";
		
		TreeNode tn = (TreeNode) element;
		
		switch(tn.getType()){
			case TreeNode.TYPE_CATORY_FORUM:
				text = "Forums";
				break;
			case TreeNode.TYPE_CATORY_USERS:
				text = "Online Users";
				break;
			case TreeNode.TYPE_NODE_FORUM:
			case TreeNode.TYPE_NODE_USER:
				if(tn.getData() != null){
					text = tn.getData().toString();
				}else{
					text = tn.getLabel();
				}
				break;
			default:
				text = "";
		}		
		return text;
	}
	public static ImageDescriptor getImageDescriptor(String path) {

        return AbstractUIPlugin.imageDescriptorFromPlugin(DotMessengerPlugin.PLUGIN_ID, path);

    }

}
