/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package textclasification;

/**
 *
 * @author ranganath
 */
public class Classification {
    
    public static ClusterDomain doClassification(String filename,String text)
    {
    WordCluster cluster=new ConstructWordCluster().constructWordCluster();
ClusterDomain bestdomain=new ClusterDomain();
bestdomain.setDomain("Data Mining");
        int max=0;
    for(int i=0;i<cluster.cluster.size();i++)
    {
        ClusterDomain temp=cluster.cluster.get(i);
        int domainid=cluster.cluster.get(i).getDomainid();
        String domianname=cluster.cluster.get(i).getDomain();
        System.out.println("Domain ID: "+domainid+" - Domain Name: "+domianname);
    
  for(int j=0;j<cluster.cluster.get(i).getWords().size();j++)
    {
        String word=cluster.cluster.get(i).getWords().get(j);
        int wordWeight=FeatureExtraction.wordWeight(text,word);
       
        
        cluster.cluster.get(i).getWeights().set(j,wordWeight);
        System.out.println("Word :"+word+"\t+Weight :"+wordWeight);
        
        if(max<wordWeight)
        {
            max=wordWeight;
            bestdomain=temp;
        }
    }
    }
return bestdomain;
    }

}
