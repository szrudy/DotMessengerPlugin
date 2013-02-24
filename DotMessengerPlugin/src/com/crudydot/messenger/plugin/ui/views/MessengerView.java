package com.crudydot.messenger.plugin.ui.views;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.crudydot.messenger.plugin.DotMessengerPlugin;


/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

public class MessengerView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "com.rudydot.swidIMP.ui.views.MessengerView";

	private TreeViewer viewer;
	private PageBook pageBook;
	private Text inputText;
	private Button btnPaste;
	private Button btnInvite;
	
	private DrillDownAdapter drillDownAdapter;
	private Action action1;
	private Action action2;
	private Action doubleClickAction;

	/**
	 * The constructor.
	 */
	public MessengerView() {
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		
		Composite container = new Composite(parent, SWT.None);
		GridLayoutFactory glf = GridLayoutFactory.swtDefaults().numColumns(3).equalWidth(false);
		
		glf.applyTo(container);
		
		Composite treeContainer = new Composite(container, SWT.NONE);
		treeContainer.setLayout(new FillLayout());
		GridData gdTreeContainer = new GridData(GridData.FILL_BOTH);
		gdTreeContainer.verticalSpan = 3;
		gdTreeContainer.minimumWidth = 80;
		
		treeContainer.setLayoutData(gdTreeContainer);
		treeContainer.setSize(100, 80);
		
		viewer = new TreeViewer(treeContainer, SWT.MULTI |SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setAutoExpandLevel(TreeViewer.ALL_LEVELS);
		drillDownAdapter = new DrillDownAdapter(viewer);
		viewer.setContentProvider(new TreeContentProvider());
		viewer.setLabelProvider(new TreeLabelProvider());
		viewer.setSorter(new ViewerSorter());
		viewer.setInput(getViewSite());
		
		GridData gdPageBook = new GridData(GridData.FILL_BOTH);
		gdPageBook.horizontalSpan = 2;
		gdPageBook.minimumWidth = 300;
		gdPageBook.minimumHeight = 80;
		this.pageBook = new PageBook(container, SWT.BORDER);
		this.pageBook.setLayoutData(gdPageBook);
	
		GridData gdText = new GridData(GridData.FILL_BOTH | GridData.HORIZONTAL_ALIGN_BEGINNING);
		gdText.minimumHeight = 45;
		gdText.minimumWidth = 60;
		gdText.verticalSpan = 2;
		this.inputText = new Text(container, SWT.BORDER | SWT.WRAP);
		this.inputText.setLayoutData(gdText);
		this.inputText.setSize(60, 45);
		
		GridData gdBtn = new GridData(GridData.CENTER);
		gdBtn.minimumHeight = 200;
		gdBtn.minimumWidth = 60;
		
		this.btnPaste = new Button(container, SWT.CENTER );
		this.btnPaste.setText("Paste");
		this.btnPaste.setLayoutData(gdBtn);
		this.btnPaste.setSize(80, 20);
		this.btnInvite = new Button(container, SWT.CENTER);
		this.btnInvite.setText("Invite");
		this.btnInvite.setLayoutData(gdBtn);
		this.btnInvite.setSize(80, 20);
		
		this.btnInvite.addMouseListener(new MouseListener(){

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
			
			
		});
		/*
		Composite rightPanel = new Composite(container, SWT.NONE);
		GridLayoutFactory glf2 = GridLayoutFactory.swtDefaults().numColumns(1).equalWidth(true);
		glf2.applyTo(rightPanel);
		
		this.senderPanel = new SenderActionPanel(rightPanel, SWT.BORDER);
		*/
		// Create the help context id for the viewer's control
		PlatformUI.getWorkbench().getHelpSystem().setHelp(viewer.getControl(), "GenericMessengerPlugin.viewer");
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				MessengerView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(action1);
		manager.add(new Separator());
		manager.add(action2);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(action1);
		manager.add(action2);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(action1);
		manager.add(action2);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
	}

	private void makeActions() {
		action1 = new Action() {
			public void run() {
				showMessage("Action 1 executed");
			}
		};
		action1.setText("Action 1");
		action1.setToolTipText("Action 1 tooltip");
		action1.setImageDescriptor(getImageDescriptor("icons/chat_run.gif"));
		
		action2 = new Action() {
			public void run() {
				showMessage("Action 2 executed");
			}
		};
		action2.setText("Action 2");
		action2.setToolTipText("Action 2 tooltip");
		action2.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
				getImageDescriptor(ISharedImages.IMG_ELCL_STOP));
		doubleClickAction = new Action() {
			public void run() {
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				showMessage("Double-click detected on "+obj.toString());
			}
		};
	}
	public static ImageDescriptor getImageDescriptor(String path) {

        return AbstractUIPlugin.imageDescriptorFromPlugin(DotMessengerPlugin.PLUGIN_ID, path);

    }
	

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}
	private void showMessage(String message) {
		MessageDialog.openInformation(
			viewer.getControl().getShell(),
			"Messenger View",
			message);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}