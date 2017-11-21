package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import model.Storage;

import model.CDCollection;

import view.CollectApp;

/**
 * 
 * controller for CD collection fxml
 * @author jesusnieto
 *
 */
public class CDController {
	@FXML
	public Button backButton;
	public Button insertButton;
	public Button deleteButton;
	public Button updateButton;
	public TextField artistField;
	public TextField albumField;
	public TextField runningField;
	public TextField multiField;
	public TextField posterField;
	public TextField enhancedField;
	public TextField yearField;
	@FXML
	TableView<CDCollection> cdTable;
	@FXML
	TableColumn<CDCollection, String> artistColumn;
	@FXML
	TableColumn<CDCollection, String> albumColumn;
	@FXML
	TableColumn<CDCollection, String> yearColumn;
	@FXML
	TableColumn<CDCollection, String> runningColumn;
	@FXML
	TableColumn<CDCollection, String> multiColumn;
	@FXML
	TableColumn<CDCollection, String> posterColumn;
	@FXML
	TableColumn<CDCollection, String> enhancedColumn;
	ObservableList<CDCollection> allCD;
	
	public void initialize() {
		cellValueFactory();
		getCDFromFile();
	}
		
	public void backButtonHandle(){
		CollectApp.stage.show();
		CollectController.childScene.hide();
		allCD = cdTable.getItems();
		try {
			writeCDFile();
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	public void insertButtonHandle() {
		CDCollection cdAdd = new CDCollection();
		if(artistField.getText().equals("")) {
			cdAdd.setArtist("");
		}else {
			cdAdd.setArtist(artistField.getText());
		}
		
		if(albumField.getText().equals("")) {
			cdAdd.setAlbum("");
		}else {
			cdAdd.setAlbum(albumField.getText());
		}
		
		if(runningField.getText().equals("")) {
			cdAdd.setRunningTime("0");
		}else {
			cdAdd.setRunningTime(runningField.getText());
		}
		
		if(multiField.getText().equals("")) {
			cdAdd.setMultiCD("");
		}else {
			cdAdd.setMultiCD(multiField.getText());
		}
		
		if(posterField.getText().equals("")) {
			cdAdd.setPoster("");
		}else {
			cdAdd.setPoster(posterField.getText());
		}
		
		if(enhancedField.getText().equals("")) {
			cdAdd.setEnhancedCD("");
		}else {
			cdAdd.setEnhancedCD(enhancedField.getText());
		}
		
		if(yearField.getText().equals("")) {
			cdAdd.setYear("");
		}else {
			cdAdd.setYear(yearField.getText());
		}
		
		cdTable.getItems().add(cdAdd);
		artistField.clear();
		albumField.clear();
		runningField.clear();
		multiField.clear();
		posterField.clear();
		enhancedField.clear();
		yearField.clear();
		
		allCD = cdTable.getItems();
		try {
			writeCDFile();
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
	}
	
	public void deleteButtonHandle() {
		ObservableList<CDCollection> cdSelected;
		allCD = cdTable.getItems();
		cdSelected = cdTable.getSelectionModel().getSelectedItems();
		cdSelected.forEach(allCD::remove);
		
		allCD = cdTable.getItems();
		try {
			writeCDFile();
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
	}
	
	public void updateButtonHandle() {
		allCD = cdTable.getItems();
		try {
			writeCDFile();
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	public void cellValueFactory() {
		artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));
		albumColumn.setCellValueFactory(new PropertyValueFactory<>("album"));
		runningColumn.setCellValueFactory(new PropertyValueFactory<>("runningTime"));
		multiColumn.setCellValueFactory(new PropertyValueFactory<>("multiCD"));
		posterColumn.setCellValueFactory(new PropertyValueFactory<>("poster"));
		enhancedColumn.setCellValueFactory(new PropertyValueFactory<>("enhancedCD"));
		yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
		
		// code below lets you edit table cells
		cdTable.setEditable(true);
		artistColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		albumColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		runningColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		multiColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		posterColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		enhancedColumn.setCellFactory(TextFieldTableCell.forTableColumn());
	}
	
	public void changeArtist(CellEditEvent edditedCell) {
		CDCollection artistSelected = cdTable.getSelectionModel().getSelectedItem();
		artistSelected.setArtist(edditedCell.getNewValue().toString());
		
	}
	
	public void changeAlbum(CellEditEvent edditedCell) {
		CDCollection albumSelected = cdTable.getSelectionModel().getSelectedItem();
		albumSelected.setAlbum(edditedCell.getNewValue().toString());
	}
	
	public void changeRunning(CellEditEvent edditedCell) {
		CDCollection runSelected = cdTable.getSelectionModel().getSelectedItem();
		runSelected.setRunningTime(edditedCell.getNewValue().toString());
	}
	
	public void changeMulti(CellEditEvent edditedCell) {
		CDCollection multiSelected = cdTable.getSelectionModel().getSelectedItem();
		multiSelected.setMultiCD(edditedCell.getNewValue().toString());
	}
	
	public void changePoster(CellEditEvent edditedCell) {
		CDCollection posterSelected = cdTable.getSelectionModel().getSelectedItem();
		posterSelected.setPoster(edditedCell.getNewValue().toString());
	}
	
	public void changeEnhanced(CellEditEvent edditedCell) {
		CDCollection enhancedSelected = cdTable.getSelectionModel().getSelectedItem();
		enhancedSelected.setEnhancedCD(edditedCell.getNewValue().toString());
	}
	
	public void changeYear(CellEditEvent edditedCell) {
		CDCollection yearSelected = cdTable.getSelectionModel().getSelectedItem();
		yearSelected.setYear(edditedCell.getNewValue().toString());
	}
	
	private void getCDFromFile() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("cd.txt")));
			String line;
			String[] array;
			while ((line = br.readLine()) != null) {
				array = line.split(",");
				cdTable.getItems().add(new CDCollection(array[0],array[1],array[2],array[3],array[4], array[5], array[6] ));	
			}
			br.close();
			
		}catch (Exception ex) {
			ex.printStackTrace();
			
		}
		
	}
	
	private void writeCDFile() throws Exception{
		Writer write = null;
		try {
			File file = new File("cd.txt");
			write = new BufferedWriter(new FileWriter(file));
			for(CDCollection cd : allCD) {
				String text = cd.getArtist() + "," + cd.getAlbum() + "," + cd.getYear() + ","  + cd.getRunningTime() + "," + cd.getMultiCD() + "," + cd.getPoster() + "," + cd.getEnhancedCD() + "\n";
				write.write(text);
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
			
			
		}finally {
			write.flush();
			write.close();
		}
		
		
		
	}

}