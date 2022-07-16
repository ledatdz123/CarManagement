/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author My-PC
 */
public class LOTRINH {
    private String ma;
    private String ten;
    private String noiDi;
    private String noiDen;
    private int khoangCach;

    public LOTRINH(String ma,String ten, String noiDi, String noiDen, int khoangCach) {
        this.ma = ma;
        this.ten = ten;
        this.noiDi = noiDi;
        this.noiDen = noiDen;
        this.khoangCach = khoangCach;
    }
    public LOTRINH(){
        
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    
    public String getNoiDi() {
        return noiDi;
    }

    public void setNoiDi(String noiDi) {
        this.noiDi = noiDi;
    }

    public String getNoiDen() {
        return noiDen;
    }

    public void setNoiDen(String noiDen) {
        this.noiDen = noiDen;
    }

    public int getKhoangCach() {
        return khoangCach;
    }

    public void setKhoangCach(int khoangCach) {
        this.khoangCach = khoangCach;
    }
    
     private static HttpURLConnection con;
   
    public static List<LOTRINH> DSLT()
	{
                List<LOTRINH> list = null;
		String str= "http://api-xe-khach.herokuapp.com/route";
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
					list = Arrays.asList(mapper.readValue(response.toString(), LOTRINH[].class));
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
   public static void loadDSLT(JTable tb){
        List<LOTRINH> list = DSLT();
        DefaultTableModel dtm= (DefaultTableModel ) tb.getModel();
        dtm.setNumRows(0);
        for(LOTRINH i: list){
            Object[] obj = {i.getMa(), i.getNoiDi(), i.getNoiDen(), i.getKhoangCach()};
            dtm.addRow(obj);
        }
        tb.setModel(dtm);
    }
   
    public static void loadCBLT(JComboBox cb){
        List<LOTRINH> list = DSLT();
        cb.removeAllItems();
        for(LOTRINH i: list){
            cb.addItem(i.getMa());
        }
    }
    
    public static String loadClick_CBLT(String noidi, String noiden){
        List<LOTRINH> list = DSLT();
        String malt = null;
        for (LOTRINH i : list) {
            if ((i.getNoiDi().equals(noidi)) && (i.getNoiDen().equals(noiden))) {
                malt = i.getMa();                
            }
        }
        return malt;
    }
    
    public static LOTRINH InsertRoute(LOTRINH lt)
	{        
		String str= "http://api-xe-khach.herokuapp.com/route";
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
			System.out.println("Vo api insert Route");
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String jsonInputString = ow.writeValueAsString(lt);
			con.getOutputStream().write(jsonInputString.getBytes());
			System.out.println(jsonInputString);
                        int responseCode = con.getResponseCode();
			System.out.println("POST Response KH Code :: " + responseCode);
		} catch (MalformedURLException e) {
			System.out.println("Loi trong api insert route1 "+ e.getMessage());
			//e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Loi trong api insert route2 " + e.getMessage());
			//e.printStackTrace();
		}
		return lt;
	}
    
    public static LOTRINH UpdateRoute(LOTRINH lt)
	{        
		String str= "http://api-xe-khach.herokuapp.com/route";
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
			System.out.println("Vo api Update Route");
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String jsonInputString = ow.writeValueAsString(lt);
			con.getOutputStream().write(jsonInputString.getBytes());
			System.out.println(jsonInputString);
                        int responseCode = con.getResponseCode();
			System.out.println("PUT Response KH Code :: " + responseCode);
		} catch (MalformedURLException e) {
			System.out.println("Loi trong api update route1 "+ e.getMessage());
			//e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Loi trong api update route2 " + e.getMessage());
			//e.printStackTrace();
		}
		return lt;
	}

    
     public static LOTRINH DeleteRoute(String id)
	{        
                LOTRINH lt = new LOTRINH();
		String str= "http://api-xe-khach.herokuapp.com/route/" + id;
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
			System.out.println("Vo api Delete Route");
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String jsonInputString = ow.writeValueAsString(lt);
			con.getOutputStream().write(jsonInputString.getBytes());
			System.out.println(jsonInputString);
                        int responseCode = con.getResponseCode();
			System.out.println("DELETE Response KH Code :: " + responseCode);
		} catch (MalformedURLException e) {
			System.out.println("Loi trong api delete route1 "+ e.getMessage());
			//e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Loi trong api delete route2 " + e.getMessage());
			//e.printStackTrace();
		}
		return lt;
	}
}
