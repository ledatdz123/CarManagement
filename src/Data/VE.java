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
import java.util.Arrays;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author My-PC
 */
public class VE {
    private int maVe;
    private String maCX;
    private String tenKH;
    private String email;
    private String sdt;
    private String ngayDat;
    private String maGhe;
    private int trangThai;
    private String ghiChu;

    public VE(int maVe, String maCX, String tenKH, String email, String sdt, String ngayDat, String maGhe, int trangThai, String ghiChu) {
        this.maVe = maVe;
        this.maCX = maCX;
        this.tenKH = tenKH;
        this.email = email;
        this.sdt = sdt;
        this.ngayDat = ngayDat;
        this.maGhe = maGhe;
        this.trangThai = trangThai;
        this.ghiChu = ghiChu;
    }
    
    public VE(){
        
    }

    public int getMaVe() {
        return maVe;
    }

    public void setMaVe(int maVe) {
        this.maVe = maVe;
    }

    public String getMaCX() {
        return maCX;
    }

    public void setMaCX(String maCX) {
        this.maCX = maCX;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(String ngayDat) {
        this.ngayDat = ngayDat;
    }

    public String getMaGhe() {
        return maGhe;
    }

    public void setMaGhe(String maGhe) {
        this.maGhe = maGhe;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
    
    private static HttpURLConnection con;    
    public static List<VE> DSVE()
	{
                List<VE> list = null;
		String str= "http://api-xe-khach.herokuapp.com/ticket";
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
					list = Arrays.asList(mapper.readValue(response.toString(),VE[].class));
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
    
    public static void loadDSKhach(JTable tb, String maCX){
        List<VE> list = DSVE();
        DefaultTableModel dtm = (DefaultTableModel) tb.getModel();
        dtm.setNumRows(0);
        for (VE i : list) {
            if (i.getMaCX().equals(maCX)) {
                if (i.getTrangThai() == 1) {
                    Object[] obj = {i.getMaVe(), i.getTenKH(), i.getEmail(), i.getSdt()};
                    dtm.addRow(obj);
                }    
            }
        }
        tb.setModel(dtm);
    }
    
    public static VE DeleteTicket(int  id)
	{        
            VE ve= new VE();
		String str= "http://api-xe-khach.herokuapp.com/ticket/" +id;
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
			System.out.println("Vo api DELETE ticket");
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String jsonInputString = ow.writeValueAsString(ve);
			con.getOutputStream().write(jsonInputString.getBytes());
			System.out.println(jsonInputString);
                        int responseCode = con.getResponseCode();
			System.out.println("DELETE Response KH Code :: " + responseCode);
		} catch (MalformedURLException e) {
			System.out.println("Loi trong api DELETE ticket1 "+ e.getMessage());
			//e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Loi trong api DELETE ticket2 " + e.getMessage());
			//e.printStackTrace();
		}
                return ve;
	}
}
