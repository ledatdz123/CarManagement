/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import com.fasterxml.jackson.databind.ObjectMapper;
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
public class DOANHTHU {
    private int maXe;
    private int tongTien;

    public DOANHTHU(int maXe, int tongTien) {
        this.maXe = maXe;
        this.tongTien = tongTien;
    }
    
    public DOANHTHU(){
        
    }

    public int getMaXe() {
        return maXe;
    }

    public void setMaXe(int maXe) {
        this.maXe = maXe;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }
    
    private static HttpURLConnection con;    
    public static List<DOANHTHU> DSDT(String ngayBD, String ngayKT)
	{
                List<DOANHTHU> list = null;
		String str= "http://api-xe-khach.herokuapp.com/turnovers?ngayBD=" + ngayBD +"&ngayKT=" + ngayKT;
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
					list = Arrays.asList(mapper.readValue(response.toString(),DOANHTHU[].class));
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
    public static void loadDoanhThu(JTable tb,String ngayBD, String ngayKT){
        List<DOANHTHU> list = DSDT(ngayBD, ngayKT);
        DefaultTableModel dtm= (DefaultTableModel ) tb.getModel();
        dtm.setNumRows(0);
        for(DOANHTHU i: list){
            Object[] obj = {i.getMaXe(), i.getTongTien()};
            dtm.addRow(obj);
        }
        tb.setModel(dtm);
    }
}
