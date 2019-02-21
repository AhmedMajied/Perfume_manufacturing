package application;

import java.io.File;
import java.io.IOException;
import java.util.Vector;
import Models.*;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExcelUtility {
	
	private WritableWorkbook workbook;
	private WritableSheet sheet;
	private WritableFont font;
	private WritableCellFormat header_cell_format;
	private WritableCellFormat data_cell_format;
	
	public ExcelUtility() {
		
		// header cell style
		font = new WritableFont(WritableFont.createFont("Calibri"),12,WritableFont.BOLD);
		header_cell_format = new WritableCellFormat(font);
		
		// table cell style
		data_cell_format = new WritableCellFormat();
	}
	
	public void generate_sold_bottles_report(){
		Vector<ManufacturedBottle> bottles = DBConnection.retrieve_sold_bottles();
		
		try{
			workbook = Workbook.createWorkbook(new File("Sold bottles report.xls"));
			sheet = workbook.createSheet("Sheet1",0);
			
			// center content of cells
			header_cell_format.setAlignment(Alignment.CENTRE);
			data_cell_format.setAlignment(Alignment.CENTRE);
			
			// write table header
			sheet.addCell(new jxl.write.Label(0,0,"Bottle Size",header_cell_format));
			sheet.addCell(new jxl.write.Label(1,0,"Liquid Name",header_cell_format));
			sheet.addCell(new jxl.write.Label(2,0,"Used Grams",header_cell_format));
			sheet.addCell(new jxl.write.Label(3,0,"Cost",header_cell_format));
			sheet.addCell(new jxl.write.Label(4,0,"Selling Price",header_cell_format));
			sheet.addCell(new jxl.write.Label(5,0,"Description",header_cell_format));
			sheet.addCell(new jxl.write.Label(6,0,"Date",header_cell_format));

			// write table content
			for(int row=0;row<bottles.size();row++) {
				sheet.addCell(new jxl.write.Label(0,row+1,bottles.get(row).name,data_cell_format));
				sheet.addCell(new jxl.write.Label(1,row+1,bottles.get(row).liquid_name,data_cell_format));
				sheet.addCell(new Number(2,row+1,bottles.get(row).used_grams,data_cell_format));
				sheet.addCell(new Number(3,row+1,bottles.get(row).cost,data_cell_format));
				sheet.addCell(new Number(4,row+1,bottles.get(row).selling_price,data_cell_format));
				sheet.addCell(new jxl.write.Label(5,row+1,bottles.get(row).description,data_cell_format));
				sheet.addCell(new jxl.write.Label(6,row+1,bottles.get(row).date,data_cell_format));
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
	
	public void generate_materials_report(Vector<Liquid> liquids, Vector<Flavor> flavors, Vector<Bottle> bottles) {
		int col_number = 10;
		Vector<Material> materials = new Vector<>();
		
		try{
			workbook = Workbook.createWorkbook(new File("Materials report.xls"));
			sheet = workbook.createSheet("Sheet1",0);
			
			// center content of cells
			header_cell_format.setAlignment(Alignment.CENTRE);
			data_cell_format.setAlignment(Alignment.CENTRE);
			
			// write table title
			sheet.addCell(new jxl.write.Label(0,0,"Liquids",header_cell_format));
			sheet.mergeCells(0, 0, 2, 0);
			sheet.addCell(new jxl.write.Label(4,0,"Flavors",header_cell_format));
			sheet.mergeCells(4, 0, 6, 0);
			sheet.addCell(new jxl.write.Label(8,0,"Bottles",header_cell_format));
			sheet.mergeCells(8, 0, 10, 0);
			
			// write table header
			for(int i=0;i<col_number;i+=4) {
				sheet.addCell(new jxl.write.Label(i,1,"Name",header_cell_format));
				sheet.addCell(new jxl.write.Label(i+1,1,"Quantity",header_cell_format));
				sheet.addCell(new jxl.write.Label(i+2,1,"Unit Cost",header_cell_format));			
			}
			
			// convert liquids to materials then write it to the sheet
			for(int i=0;i<liquids.size();i++) {
				materials.add(liquids.get(i));
			}
			write_materials_data(materials, 0);
			materials.clear();
			
			// convert flavors to materials then write it to the sheet
			for(int i=0;i<flavors.size();i++) {
				materials.add(flavors.get(i));
			}
			write_materials_data(materials, 4);
			materials.clear();
			
			// convert bottles to materials then write it to the sheet
			for(int i=0;i<bottles.size();i++) {
				materials.add(bottles.get(i));
			}
			write_materials_data(materials, 8);

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
	
	public void generate_needed_materials_report() {
		int col_number = 10;
		
		try{
			workbook = Workbook.createWorkbook(new File("Needed materials report.xls"));
			sheet = workbook.createSheet("Sheet1",0);
			
			// center content of cells
			header_cell_format.setAlignment(Alignment.CENTRE);
			data_cell_format.setAlignment(Alignment.CENTRE);
			
			// write table title
			sheet.addCell(new jxl.write.Label(0,0,"Needed Liquids",header_cell_format));
			sheet.mergeCells(0, 0, 2, 0);
			sheet.addCell(new jxl.write.Label(4,0,"Needed Flavors",header_cell_format));
			sheet.mergeCells(4, 0, 6, 0);
			sheet.addCell(new jxl.write.Label(8,0,"Needed Bottles",header_cell_format));
			sheet.mergeCells(8, 0, 10, 0);
			
			// write table header
			for(int i=0;i<col_number;i+=4) {
				sheet.addCell(new jxl.write.Label(i,1,"Name",header_cell_format));
				sheet.addCell(new jxl.write.Label(i+1,1,"Quantity",header_cell_format));
				sheet.addCell(new jxl.write.Label(i+2,1,"Unit Cost",header_cell_format));			
			}
			
			// write needed materials table
			write_materials_data(DBConnection.retrieve_reorder_materials("Liquid"),0);
			write_materials_data(DBConnection.retrieve_reorder_materials("Flavor"),4);
			write_materials_data(DBConnection.retrieve_reorder_materials("Bottle"),8);

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
	
	private void write_materials_data(Vector<Material> needed_materials,int start_col){
		for(int row=0;row<needed_materials.size();row++) {
			try {
				sheet.addCell(new jxl.write.Label(start_col,row+2,needed_materials.get(row).name,data_cell_format));
				sheet.addCell(new jxl.write.Label(start_col+1,row+2,needed_materials.get(row).get_diff_quantities(),data_cell_format));
				sheet.addCell(new jxl.write.Label(start_col+2,row+2,needed_materials.get(row).unit_costs,data_cell_format));
			} catch (RowsExceededException e) {
				e.printStackTrace();
			} catch (WriteException e) {
				e.printStackTrace();
			}
		}
	}
	
}
