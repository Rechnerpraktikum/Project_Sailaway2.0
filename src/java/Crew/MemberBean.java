/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Crew;


import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


/**
 *
 * @author h1250632
 */

@ManagedBean
@SessionScoped

public class MemberBean {
    private List<Member> members=null;
  
    @PostConstruct
    public void init(){
        members=new ArrayList<Member>();
        members.add(new Member("Nurcan", "Altin", 1250632));
        members.add(new Member("Emine", "Bas", 1452558));
        members.add(new Member("Eisabeth", "Floh", 1451040));  
        members.add(new Member("Benjamin", "Obermüller", 1149781));
        members.add(new Member("Manuel", "Sampl", 1025149));
        
        
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
    

  
 
}

