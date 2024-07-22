/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bcast;

import java.io.Serializable;


/**
 *
 * @author kenomik
 */

public class Rule implements Serializable {
    
    public String ruleName;
    public String condition;
    
    public Rule(String rulename, String condition) {
        this.ruleName = rulename;
        this.condition = condition;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
    
}
