/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;


import ModelInput.Car;
import com.fasterxml.jackson.databind.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Arrays;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author My-PC
 */
public class XE {
    private int maXe;
    private String bienSo;
    private String loaiXe;
    private int soLuongGhe;
    private float gia;

    public XE(int maXe, String bienSo, String loaiXe, int soLuongGhe, float gia) {
        this.maXe = maXe;
        this.bienSo = bienSo;
        this.loaiXe = loaiXe;
        this.soLuongGhe = soLuongGhe;
        this.gia = gia;
    }

    public XE(){
        
    }
    public int getMaXe() {
        return maXe;
    }

    public void setMaXe(int maXe) {
        this.maXe = maXe;
    }

    public String getBienSo() {
        return bienSo;
    }

    public void setBienSo(String bienSo) {
        this.bienSo = bienSo;
    }

    public String getLoaiXe() {
        return loaiXe;
    }

    public void setLoaiXe(String loaiXe) {
        this.loaiXe = loaiXe;
    }

    public int getSoLuongGhe() {
        return soLuongGhe;
    }

    public void setSoLuongGhe(int soLuongGhe) {
        this.soLuongGhe = soLuongGhe;
    }

    public float getGia() {
        return gia;
    }

    public void setGia(float gia) {
        this.gia = gia;
    }
    
    
    private static HttpURLConnection con;
   
    public static List<XE> DSXE()
	{
                List<XE> list = null;
		String str= "http://api-xe-khach.herokuapp.com/bus";
		try {
			URL url = new URL(str);
			con= (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
 
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				//System.out.println(response.toString());
				if(response.toString().trim().equals("")==false)
				{
					ObjectMapper mapper = new ObjectMapper();
					list = Arrays.asList(mapper.readValue(response.toString(), XE[].class));
				}                                
			} else {
				System.out.println("GET request not worked");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
     
    public static void loadDSXE(JTable tb){
        List<XE> list = DSXE();
        DefaultTableModel dtm= (DefaultTableModel ) tb.getModel();
        dtm.setNumRows(0);
        for(XE i: list){
            Object[] obj = {i.getMaXe(), i.getBienSo(), i.getLoaiXe(), i.getSoLuongGhe(), i.getGia()};
            dtm.addRow(obj);
        }
        tb.setModel(dtm);
    }
    
    public static void loadCBXE(JComboBox cb){
        List<XE> list = DSXE();
        cb.removeAllItems();
        for(XE i: list){
            cb.addItem(i.getMaXe());
        }
    }

    
    public static Car InsertCar(XE xe)
	{        
                Car car = new Car(xe);
		String str= "http://api-xe-khach.herokuapp.com/bus";
		try {
			URL url = new URL(str);
			con= (HttpURLConnection)url.openConnection();
			con.setRequestMethod("POST");
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Accept", "application/json");
			con.setDoOutput(true);
			con.setDoInput(true);
			System.out.println("Vo api insert Order");
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String jsonInputString = ow.writeValueAsString(car);
			con.getOutputStream().write(jsonInputString.getBytes());
			System.out.println(jsonInputString);
                        int responseCode = con.getResponseCode();
			System.out.println("POST Response KH Code :: " + responseCode);
		} catch (MalformedURLException e) {
			System.out.println("Loi trong api insert car1 "+ e.getMessage());
			//e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Loi trong api insert car2 " + e.getMessage());
			//e.printStackTrace();
		}
		return car;
	}
    public static Car UpdateCar(XE xe, int id)
	{     
                Car car = new Car(xe);
		String str= "http://api-xe-khach.herokuapp.com/bus/" + id;
		try {
			URL url = new URL(str);
			con= (HttpURLConnection)url.openConnection();
			con.setRequestMethod("PUT");
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Accept", "application/json");
			con.setDoOutput(true);
			con.setDoInput(true);
			System.out.println("Vo api UPDATE Car");
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String jsonInputString = ow.writeValueAsString(car);
			con.getOutputStream().write(jsonInputString.getBytes());
			System.out.println(jsonInputString);
                        int responseCode = con.getResponseCode();
			System.out.println("PUT Response KH Code :: " + responseCode);
		} catch (MalformedURLException e) {
			System.out.println("Loi trong api UPDATE Car 1 "+ e.getMessage());
			//e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Loi trong api iUPDATE Car 1 " + e.getMessage());
			//e.printStackTrace();
		}
		return car;
	}
    
     public static XE DeleteCar(int id)
	{        
            XE xe= new XE();
		String str= "http://api-xe-khach.herokuapp.com/bus?id=" +id;
		try {
			URL url = new URL(str);
			con= (HttpURLConnection)url.openConnection();
			con.setRequestMethod("DELETE");
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Accept", "application/json");
			con.setDoOutput(true);
			con.setDoInput(true);
			System.out.println("Vo api DELETE CAR");
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String jsonInputString = ow.writeValueAsString(xe);
			con.getOutputStream().write(jsonInputString.getBytes());
			System.out.println(jsonInputString);
                        int responseCode = con.getResponseCode();
			System.out.println("DELETE Response KH Code :: " + responseCode);
		} catch (MalformedURLException e) {
			System.out.println("Loi trong api DELETE CAR1 "+ e.getMessage());
			//e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Loi trong api DELETE CAR2 " + e.getMessage());
			//e.printStackTrace();
		}
                return xe;
	}
}
