/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package textclasification;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author ranganath
 */
public class ConstructWordCluster {
    
    public static  WordCluster word;
    
public static WordCluster  constructWordCluster()
{
    if(word==null)
    {
        System.out.println("Word Cluster construction Started");
    word=new WordCluster();
    try{

Class.forName(DataBase.Driver_Class);
Connection con=DriverManager.getConnection(DataBase.Connection_URL,DataBase.UserID,DataBase.Pasword);
PreparedStatement ps_domains=con.prepareStatement("select * from Domains");
ResultSet rs_domains=ps_domains.executeQuery();

while(rs_domains.next())
{
    ClusterDomain domain=new ClusterDomain();
    domain.setDomainid(rs_domains.getInt(1));
    domain.setDomain(rs_domains.getString(2));
    word.cluster.add(domain);
    
}
rs_domains.close();
PreparedStatement ps_words=con.prepareStatement("select * from wordcluster where domainid=?");
for(int i=0;i<word.cluster.size();i++)
{
ps_words.setInt(1,word.cluster.get(i).getDomainid());
ResultSet rs_words=ps_words.executeQuery();

while(rs_words.next())
{
    word.cluster.get(i).getWords().add(rs_words.getString(2));
    word.cluster.get(i).getWeights().add(0);
}

rs_words.close();
}
con.close();
}catch(Exception ex){ex.printStackTrace();}
}
return word;
}
}
