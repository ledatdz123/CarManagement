/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelInput;

/**
 *
 * @author My-PC
 */
import Data.*;
public class Car {
    private String bienSo;
    private String loaiXe;
    private int soLuongGhe;
    private float gia;

    public Car(String bienSo, String loaiXe, int soLuongGhe, float gia) {
        this.bienSo = bienSo;
        this.loaiXe = loaiXe;
        this.soLuongGhe = soLuongGhe;
        this.gia = gia;
    }
    public Car(){
        
    }
    public Car(XE xe){
        this.bienSo = xe.getBienSo();
        this.loaiXe = xe.getLoaiXe();
        this.soLuongGhe = xe.getSoLuongGhe();
        this.gia = xe.getGia();
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
    
    
}
