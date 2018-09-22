package application;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import Models.ManufacturedBottle;
import jxl.*;
import jxl.format.Alignment;
import jxl.write.*;
import jxl.write.Number;
import jxl.write.biff.RowsExceededException;


public class Utility {
	
	public static Pane prepare_cell(String text){
		
		Pane cell = new Pane();
		
		Label label = new Label(text);
		label.setAlignment(Pos.CENTER);
		label.prefWidthProperty().bind(cell.widthProperty());
		
		cell.getChildren().add(label);
		HBox.setHgrow(cell, Priority.ALWAYS);
		
		//cell.setStyle("-fx-border-style: solid inside;"+"-fx-border-color: red;");
		//label.setStyle("-fx-border-style: solid inside;"+"-fx-border-color: blue;");
		
		return cell;
	}
	
	public static TextField prepare_editable_cell(String text,boolean editable) {
		
		TextField field = new TextField(text);
		field.setAlignment(Pos.CENTER);
		
		if(!editable)
			field.setDisable(true);
		
		HBox.setHgrow(field, Priority.ALWAYS);
		
		return field;
	}
	
	public static void generate_excel_file(){
		Vector<ManufacturedBottle> bottles = DBConnection.retrieve_manufactured_bottles();
		
		try{
			WritableWorkbook workbook = Workbook.createWorkbook(new File("sold bottles report.xls"));
			WritableSheet sheet = workbook.createSheet("Sheet1",0);
			
			// header cell style
			WritableFont font = new WritableFont(WritableFont.createFont("Calibri"),12,WritableFont.BOLD);
			WritableCellFormat header_cell_format = new WritableCellFormat(font);
			header_cell_format.setAlignment(Alignment.CENTRE);
			
			// write table header
			sheet.addCell(new jxl.write.Label(0,0,"Bottle Size",header_cell_format));
			sheet.addCell(new jxl.write.Label(1,0,"Liquid Name",header_cell_format));
			sheet.addCell(new jxl.write.Label(2,0,"Used Grams",header_cell_format));
			sheet.addCell(new jxl.write.Label(3,0,"Cost",header_cell_format));
			sheet.addCell(new jxl.write.Label(4,0,"Selling Price",header_cell_format));
			sheet.addCell(new jxl.write.Label(5,0,"Date",header_cell_format));

			// table cell style
			WritableCellFormat data_cell_format = new WritableCellFormat();
			data_cell_format.setAlignment(Alignment.CENTRE);
			
			// write table content
			for(int row=0;row<bottles.size();row++) {
				sheet.addCell(new jxl.write.Label(0,row+1,bottles.get(row).name,data_cell_format));
				sheet.addCell(new jxl.write.Label(1,row+1,bottles.get(row).liquid_name,data_cell_format));
				sheet.addCell(new Number(2,row+1,bottles.get(row).used_grams,data_cell_format));
				sheet.addCell(new Number(3,row+1,bottles.get(row).cost,data_cell_format));
				sheet.addCell(new Number(4,row+1,bottles.get(row).selling_price,data_cell_format));
				sheet.addCell(new jxl.write.Label(5,row+1,bottles.get(row).date,data_cell_format));
			}

			workbook.write();
			workbook.close();
		}catch(IOException e) {
			e.printStackTrace();
			AlertBox.display("something went wrong while creating the file");
		} catch (RowsExceededException e) {
			e.printStackTrace();
			AlertBox.display("something went wrong while creating the file");
		} catch (WriteException e) {
			e.printStackTrace();
			AlertBox.display("something went wrong while creating the file");
		}
	}
}
