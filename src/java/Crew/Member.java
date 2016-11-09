/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Crew;

/**
 *
 * @author h1250632
 */

    public class Member {
    private String name;
    private String surname;
    private int idnumber;

    public Member(String name, String surname, int idnumber) {
        this.name = name;
        this.surname = surname;
        this.idnumber = idnumber;
    }

    public Member() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getidnumber() {
        return idnumber;
    }

    public void setidnumber(int idnumber) {
        this.idnumber = idnumber;
    }

    @Override
    public String toString() {
        return "Member{" + "name=" + name + ", surname=" + surname + ", idnumber=" + idnumber + '}';
    }
    
    
    
}

