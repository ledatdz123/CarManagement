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
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author My-PC
 */
public class CHUYENXE {
    private String maCX;
    private String noiDi;
    private String noiDen;
    private String ngayDi;
    private int khoangcach;
    private int maXe;
    private String maLT;
    private float gia;

    public CHUYENXE(String maCX, String noiDi, String noiDen, String ngayDi, int khoangcach, int maXe, String maLT, float gia) {
        this.maCX = maCX;
        this.noiDi = noiDi;
        this.noiDen = noiDen;
        this.ngayDi = ngayDi;
        this.khoangcach = khoangcach;
        this.maXe = maXe;
        this.maLT = maLT;
        this.gia = gia;
    }

   
    public CHUYENXE(){
        
    }

    public String getMaCX() {
        return maCX;
    }

    public void setMaCX(String maCX) {
        this.maCX = maCX;
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

    public String getNgayDi() {
        return ngayDi;
    }

    public void setNgayDi(String ngayDi) {
        this.ngayDi = ngayDi;
    }

    public int getKhoangcach() {
        return khoangcach;
    }

    public void setKhoangcach(int khoangcach) {
        this.khoangcach = khoangcach;
    }

   

    public int getMaXe() {
        return maXe;
    }

    public void setMaXe(int maXe) {
        this.maXe = maXe;
    }

    public String getMaLT() {
        return maLT;
    }

    public void setMaLT(String maLT) {
        this.maLT = maLT;
    }

    public float getGia() {
        return gia;
    }

    public void setGia(float gia) {
        this.gia = gia;
    }
    
    private static HttpURLConnection con;    
    public static List<CHUYENXE> DSCHUYEN()
	{
                List<CHUYENXE> list = null;
		String str= "http://api-xe-khach.herokuapp.com/tour";
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
					list = Arrays.asList(mapper.readValue(response.toString(), CHUYENXE[].class));
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
    
    public static void loadDSChuyen(JTable tb){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        List<CHUYENXE> list = DSCHUYEN();
        DefaultTableModel dtm = (DefaultTableModel) tb.getModel();
        dtm.setNumRows(0);
        try {
            for (CHUYENXE i : list) {
            Date ngayDi = format.parse(i.getNgayDi());
            if (ngayDi.after(date)) {
                Object[] obj = {i.getMaCX(), i.getNgayDi(), i.getNoiDi(), i.getNoiDen(), i.getMaXe()};
                dtm.addRow(obj);
            }
        }
        tb.setModel(dtm);
        } catch (ParseException e) {
            System.out.println("Lỗi đổi định dạng ngày đi, lấy DSChuyen!");
        }
        
    }
    public static void loadDSChuyen_NgayDi(JTable tb, String ngay){
        List<CHUYENXE> list = DSCHUYEN();
        DefaultTableModel dtm = (DefaultTableModel) tb.getModel();
        dtm.setNumRows(0);
        for (CHUYENXE i : list) {
            if (i.getNgayDi().equals(ngay)) {                
                Object[] obj = {i.getMaCX(), i.getNgayDi(),i.getNoiDi(), i.getNoiDen(), i.getMaXe()};
                dtm.addRow(obj);
            }
        }
        tb.setModel(dtm);
    }
    
    public static boolean checkMACX(String ma){
        boolean check = true;
        List<CHUYENXE> list = DSCHUYEN();
        for (CHUYENXE i : list){
            if(i.getMaCX().equals(ma)){
                return false;                
            }               
        }
        return check;
    }
 
//    public static void main(String[] args) throws ParseException {
//        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
//        Date date = new Date();
//        List<CHUYENXE> list = DSCHUYEN();
//         for (CHUYENXE i : list) {
//            Date ngayDi = format.parse(i.getNgayDi());
//             System.out.println(ngayDi);
//            if (ngayDi.after(date)) {
//                Object[] obj = {i.getMaCX(), i.getNgayDi(), i.getNoiDi(), i.getNoiDen(), i.getMaXe()};
//                System.out.println(obj.toString());
//            }
//        }
//    }
    public static CHUYENXE InsertTour(CHUYENXE cx)
	{        
		String str= "http://api-xe-khach.herokuapp.com/tour";
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
			System.out.println("Vo api insert TOUR");
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String jsonInputString = ow.writeValueAsString(cx);
			con.getOutputStream().write(jsonInputString.getBytes());
			System.out.println(jsonInputString);
                        int responseCode = con.getResponseCode();
			System.out.println("POST Response KH Code :: " + responseCode);
		} catch (MalformedURLException e) {
			System.out.println("Loi trong api insert tour1 "+ e.getMessage());
			//e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Loi trong api insert tour2 " + e.getMessage());
			//e.printStackTrace();
		}
		return cx;
	}
    
    public static CHUYENXE UpdateTour(CHUYENXE cx, String id)
	{        
		String str= "http://api-xe-khach.herokuapp.com/tour?id=" + id;
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
			String jsonInputString = ow.writeValueAsString(cx);
			con.getOutputStream().write(jsonInputString.getBytes());
			System.out.println(jsonInputString);
                        int responseCode = con.getResponseCode();
			System.out.println("PUT Response KH Code :: " + responseCode);
		} catch (MalformedURLException e) {
			System.out.println("Loi trong api update tour1 "+ e.getMessage());
			//e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Loi trong api update tour2 " + e.getMessage());
			//e.printStackTrace();
		}
		return cx;
	}
    
    
}
