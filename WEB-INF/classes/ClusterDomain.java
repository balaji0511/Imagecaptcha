/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package textclasification;

import java.util.ArrayList;

/**
 *
 * @author ranganath
 */
public class ClusterDomain {
    private String domain;
    private int domainid;
    private ArrayList<String> words=new ArrayList<String>();
    private ArrayList<Integer> weights=new ArrayList<Integer>();

    /**
     * @return the domain
     */
    public String getDomain() {
        return domain;
    }

    /**
     * @param domain the domain to set
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }

    /**
     * @return the words
     */
    public ArrayList<String> getWords() {
        return words;
    }

    /**
     * @param words the words to set
     */
    public void setWords(ArrayList<String> words) {
        this.words = words;
    }

    /**
     * @return the domainid
     */
    public int getDomainid() {
        return domainid;
    }

    /**
     * @param domainid the domainid to set
     */
    public void setDomainid(int domainid) {
        this.domainid = domainid;
    }

    /**
     * @return the weights
     */
    public ArrayList<Integer> getWeights() {
        return weights;
    }

    /**
     * @param weights the weights to set
     */
    public void setWeights(ArrayList<Integer> weights) {
        this.weights = weights;
    }
    
}
