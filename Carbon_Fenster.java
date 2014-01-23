import java.awt.Desktop;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;


public class Carbon_Fenster {
	//private DataBindingContext m_bindingContext;

	protected Shell shlCarbon;
	private Table table;
	private Combo combo;
	private Button btn_audio, btn_live, btn_pdf, btn_mitschrift, btn_pres;

	/**
	 * Launch the application.
	 * @param args
	 * 
	 */
	
	private static String user = "";
	
	static FilenameFilter onlyDirectory = new FilenameFilter() {
		  @Override
		  public boolean accept(File dir, String name) {
		    return new File(dir, name).isDirectory();
		  }
		};
	
		
	static String localFolder;
	private Text text;
	
	public static void main(String[] args) {

		//localFolder = System.getProperty("user.dir")+"\\";
		localFolder = "C:/Users/Win7-JP/Dropbox/MLS - Unterlagen/";
		
		Display display = Display.getDefault();
		Realm.runWithDefault(SWTObservables.getRealm(display), new Runnable() {
			public void run() {
				try {
					Carbon_Fenster window = new Carbon_Fenster();
					window.open();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlCarbon.open();
		shlCarbon.layout();
		
		//GET USERNAME
		JFrame frame = new JFrame("Username");
		String s = (String)JOptionPane.showInputDialog(
		                     frame,
		                     "Hi! What's your name?",
		                     "Carbon",
		                     JOptionPane.PLAIN_MESSAGE,
		                     null,
		                     null,
		                     "");

		 //If a string was returned, say so.
		 if ((s != null) && (s.length() > 0)) {
			 	user = s;
		 }
		
		shlCarbon.setText("Carbon - "+user);
		
		Button btnAdd = new Button(shlCarbon, SWT.NONE);
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				
				// Erstellung Array vom Datentyp Object, Hinzufï¿½gen der Komponenten		
				JTextField name = new JTextField();
				JTextField start = new JTextField();
				JTextField end = new JTextField();
				
			//name.setText("Mikrobiologei");
			start.setText("1.10.2012");
			end.setText("1.12.2012");
		                Object[] message = {"Name der Vorlesungsreihe:", name, 
		        		"Start der Vorlesung: (ex. 01.01.2011)", start, "Ende der Vorlesung: (ex. 01.07.2011)", end};
		 
		                JOptionPane pane = new JOptionPane( message, 
		                                                JOptionPane.PLAIN_MESSAGE, 
		                                                JOptionPane.OK_CANCEL_OPTION);
		                pane.createDialog(null, "Vorlesungsreihe hinzufï¿½gen").setVisible(true);
		 
		                String pfad = localFolder;
		                
		                String[] startD =  start.getText().split("\\.");
		                int[] startI = new int[startD.length];
		                for(int i=0;i<startD.length;i++){
		                	startI[i] = Integer.parseInt(startD[i]);
		                }
		                
		                String[] endD = end.getText().split("\\.");
		                int[] endI = new int[endD.length];
		                for(int i=0;i<endD.length;i++){
		                	endI[i] = Integer.parseInt(endD[i]);
		                }
		                
		                createOrdner.createTree(pfad, name.getText(), startI, endI);
			}
		});
		btnAdd.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		btnAdd.setBounds(721, 6, 49, 30);
		btnAdd.setText("Add");
		
		Label label_1 = new Label(shlCarbon, SWT.SEPARATOR | SWT.VERTICAL);
		label_1.setBounds(479, 50, 4, 382);
		
		/*********************/
		loadFolders(localFolder);
		/*********************/
		
		while (!shlCarbon.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/*
	 * My Code
	 */
	
	/*
	 * Function that loads all 'lectures'
	 */
	private static String[] loadFolders(String pfad){
		
		String[] subdir = new String[] {""};
		File home = new File(pfad);
		if(home.exists())	subdir = home.list(onlyDirectory);
		
		return subdir;
	}
	
	private boolean matchUser(String[] users, String user){
		
		for(String u :users){
			if(u.equals(user))	return true;
		}
		return false;
	}
	
	/*
	 * My Code End
	 */
	
	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlCarbon = new Shell();
		shlCarbon.setSize(798, 489);
		

		
		Label label = new Label(shlCarbon, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setBounds(10, 42, 760, 2);
		
		table = new Table(shlCarbon, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(10, 50, 463, 382);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setResizable(false);
		tblclmnNewColumn.setWidth(42);
		tblclmnNewColumn.setText("No.");
		
		TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_1.setResizable(false);
		tblclmnNewColumn_1.setWidth(84);
		tblclmnNewColumn_1.setText("Datum");
		
		TableColumn tblclmnNewColumn_2 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_2.setResizable(false);
		tblclmnNewColumn_2.setWidth(260);
		tblclmnNewColumn_2.setText("Title");
		
		TableColumn tblclmnStatus = new TableColumn(table, SWT.NONE);
		tblclmnStatus.setResizable(false);
		tblclmnStatus.setWidth(72);
		tblclmnStatus.setText("Status");
		
		Composite composite = new Composite(shlCarbon, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		composite.setBounds(489, 50, 281, 382);
		
		final Label lblTheme = new Label(composite, SWT.NONE);
		lblTheme.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		lblTheme.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		lblTheme.setBounds(10, 10, 271, 25);
		lblTheme.setText("Thema");
		
		final Label lblDate = new Label(composite, SWT.NONE);
		lblDate.setBounds(211, 41, 70, 20);
		lblDate.setText("Datum");
		
		final Label lblProf = new Label(composite, SWT.NONE);
		lblProf.setBounds(10, 41, 138, 20);
		lblProf.setText("Professor");
		
		btn_live = new Button(composite, SWT.CENTER);
		btn_live.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				
				try {
					Desktop.getDesktop().open(new File((String) btn_live.getData()));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btn_live.setImage(SWTResourceManager.getImage(Carbon_Fenster.class, "/org/eclipse/wb/swt/pdf_audio.png"));
		btn_live.setBounds(15, 241, 52, 60);
		
		btn_pres = new Button(composite, SWT.CENTER);
		btn_pres.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				try {
					Desktop.getDesktop().open(new File((String) btn_pres.getData()));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btn_pres.setImage(SWTResourceManager.getImage(Carbon_Fenster.class, "/org/eclipse/wb/swt/powerpoint.png"));
		btn_pres.setBounds(15, 175, 52, 60);
		
		btn_mitschrift = new Button(composite, SWT.CENTER);
		btn_mitschrift.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				try {
					Desktop.getDesktop().open(new File((String) btn_mitschrift.getData()));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btn_mitschrift.setImage(SWTResourceManager.getImage(Carbon_Fenster.class, "/org/eclipse/wb/swt/notizen.png"));
		btn_mitschrift.setBounds(82, 175, 52, 60);
		
		btn_pdf = new Button(composite, SWT.CENTER);
		btn_pdf.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				try {
					Desktop.getDesktop().open(new File((String) btn_pdf.getData()));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btn_pdf.setImage(SWTResourceManager.getImage(Carbon_Fenster.class, "/org/eclipse/wb/swt/pdf.png"));
		btn_pdf.setBounds(82, 241, 52, 60);
		
		btn_audio = new Button(composite, SWT.CENTER);
		btn_audio.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				try {
					Desktop.getDesktop().open(new File((String) btn_audio.getData()));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btn_audio.setImage(SWTResourceManager.getImage(Carbon_Fenster.class, "/org/eclipse/wb/swt/audio.png"));
		btn_audio.setBounds(149, 241, 52, 60);
		
		text = new Text(composite, SWT.BORDER | SWT.WRAP | SWT.MULTI);
		text.setEnabled(false);
		text.setBounds(10, 79, 271, 84);
		
		final Button btnBearbeitet = new Button(composite, SWT.CHECK);
		btnBearbeitet.setBounds(10, 352, 111, 20);
		btnBearbeitet.setText("bearbeitet");
		
		combo = new Combo(shlCarbon, SWT.READ_ONLY);
		combo.addSelectionListener(new SelectionAdapter() {
			//ENABLE DROP DOWN MENUE FOR LECTURES
			@Override
			public void widgetSelected(SelectionEvent e){
				//REMOVE LECTURES
				table.removeAll();
				
				//GET LECTURES
				int index = combo.getSelectionIndex();
				String lectureSeries = combo.getItem(index);
				String[] lectures = loadFolders(localFolder+lectureSeries+"/Material");
				
				//ADD LECTURES TO TABLE
				
				for(int i=0;i<lectures.length;i++){
					
					//PARSE INFO FILE
					TableItem tableItem1 = new TableItem(table,SWT.NONE);
					String pfad = localFolder + lectureSeries+"/Material/"+lectures[i];
					String[] infoFile = null;
					try {
						infoFile = InfoLoader.load(pfad);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					if(infoFile.length>3){

						if(matchUser(infoFile[3].split(","),user)){
							tableItem1.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
						}
					}
					
					String[] inhalt;
					if(infoFile!=null && infoFile.length>0){
						inhalt = new String[] {i+1+"", lectures[i], infoFile[0], infoFile[2]};
					}else{
						inhalt = new String[] {i+1+"", lectures[i],  "", "", ""};
					}
					
					tableItem1.setText(inhalt);
					
				}
			}
		});
		combo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		//Load lecture folders from path
		combo.setItems(loadFolders(localFolder));
		combo.setBounds(10, 8, 156, 28);
		m_bindingContext = initDataBindings();

		//TABLE LISTENER
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = table.getSelectionIndex();
				TableItem tI = table.getItem(index);
				//PFAD DER INFO DATEI ERZEUGEN
				String pfad = localFolder + combo.getText()+"/Material/"+tI.getText(1);
				FileList fl = new FileList(pfad);
			System.out.println(fl);
				try{
					String[] infoFile = InfoLoader.load(pfad);
					//RECHTES FENSTER Fï¿½LLEN
					lblTheme.setText(infoFile[0]);
					lblDate.setText(tI.getText(1));
					lblProf.setText(infoFile[1]);
					
					btn_audio.setEnabled(fl.audio);
					btn_audio.setData(fl.audioS);
					
					btn_pdf.setEnabled(fl.pdf);
					btn_pdf.setData(fl.pdfS);
					
					btn_live.setEnabled(fl.live);
					btn_live.setData(fl.liveS);
					
					btn_mitschrift.setEnabled(fl.text);
					btn_mitschrift.setData(fl.textS);
					
					btn_pres.setEnabled(fl.pres);
					btn_pres.setData(fl.presS);
					
					//MATCH USER
					if(infoFile.length>3) btnBearbeitet.setSelection(matchUser(infoFile[3].split(","), user));
					else btnBearbeitet.setSelection(false);
				}catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		
		//TITEL EDITIERN
		lblTheme.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				JFrame frame = new JFrame("JOptionPane showMessageDialog component example");
				 String s = (String)JOptionPane.showInputDialog(
				                     frame,
				                     "Titel: ",
				                     "Vorlesungstitel",
				                     JOptionPane.PLAIN_MESSAGE,
				                     null,
				                     null,
				                     lblTheme.getText());

				 //If a string was returned, say so.
				 if ((s != null) && (s.length() > 0)) {
				     	lblTheme.setText(s);
				     	String pfad = localFolder + combo.getText() + "/Material/" + table.getItem(table.getSelectionIndex()).getText(1);
				     		String[] inhalt = new String[] {lblTheme.getText(), lblProf.getText(), table.getItem(table.getSelectionIndex()).getText(3), ""};
							try {
								InfoLoader.save(pfad, inhalt);
								reloadTable(combo);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
				     return;
				 }
			}
		});
		
		//PROFESSOR EDITIEREN
		lblProf.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
					JFrame frame = new JFrame("JOptionPane showMessageDialog component example");
					 String s = (String)JOptionPane.showInputDialog(
					                     frame,
					                     "Professor: ",
					                     "Customized Dialog",
					                     JOptionPane.PLAIN_MESSAGE,
					                     null,
					                     null,
					                     lblTheme.getText());

					 //If a string was returned, say so.
					 if ((s != null) && (s.length() > 0)) {
					     	lblProf.setText(s);
					     	String pfad = localFolder + combo.getText() + "/Material/" + table.getItem(table.getSelectionIndex()).getText(1);
					     	String[] inhalt = new String[] {lblTheme.getText(), lblProf.getText(), table.getItem(table.getSelectionIndex()).getText(3), ""};
							try {
								InfoLoader.save(pfad, inhalt);
								reloadTable(combo);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
					     return;
					 }
				}
		});
		
		//BEARBEITET BTN 
		btnBearbeitet.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				//SAVE THE 'BEARBEITUNG' OF THE FILE
				String pfad = localFolder + combo.getText() + "/Material/" + table.getItem(table.getSelectionIndex()).getText(1);
				
				try {
					InfoLoader.saveZustand(pfad, user, btnBearbeitet.getSelection());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				//RELOAD TABLE
				reloadTable(combo);
			}
		});
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		return bindingContext;
	}
	
	private void reloadTable(Combo combo){
		
		
		{
			int rightNow = table.getSelectionIndex();
			table.removeAll();
			
			//GET LECTURES
			int index = combo.getSelectionIndex();
			String lectureSeries = combo.getItem(index)+"/Material";
			String[] lectures = loadFolders(localFolder+lectureSeries+"/");
			
			//ADD LECTURES TO TABLE
			
			for(int i=0;i<lectures.length;i++){
				
				//PARSE INFO FILE
				TableItem tableItem1 = new TableItem(table,SWT.NONE);
				String pfad1 = localFolder + lectureSeries+"/"+lectures[i];
				String[] infoFile = null;
				try {
					infoFile = InfoLoader.load(pfad1);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if(infoFile.length>3){

					if(matchUser(infoFile[3].split(","),user)){
						tableItem1.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
					}
				}
				
				String[] inhalt;
				if(infoFile!=null && infoFile.length>0){
					inhalt = new String[] {i+1+"", lectures[i], infoFile[0], infoFile[2]};
				}else{
					inhalt = new String[] {i+1+"", lectures[i],  "", "", ""};
				}
				
				tableItem1.setText(inhalt);
			}
			table.select(rightNow);
		}
	}
	
}
