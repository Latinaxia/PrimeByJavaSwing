package test.Prime;

import java.util.Arrays;
//求最小生成树
class CreatMinTree{
    //创建图的邻接矩阵
    public void creatGraph(MGraph graph,int verxs,char data[],int weight[][]){
        int i,j;
        for (i = 0; i < verxs; i++) {
            graph.data[i]=data[i];//传入图的顶点数据
            for(j=0;j<verxs;j++){
                graph.weight[i][j]=weight[i][j];//传入图的边的数据
            }
        }
    }
    //显示图的邻接矩阵
    public void showGraph(MGraph graph){
        for (int[] link:graph.weight){
            System.out.println(Arrays.toString(link));
        }
    }

    //prime算法实现,根据输入的v从第v个点开始生成最小生成树
    public int[] prime(MGraph graph,int v){
        int visited[]=new int[graph.verxs];//初始值为0，表示顶点未访问过
        int primepoints[] =new int[(graph.verxs-1)*2];
        int pp=0;
        visited[v]=1;
        int h1=-1;
        int h2=-1;
        int minweight=100;
        for (int k = 1; k < graph.verxs; k++) {//最小生成树中有verxs-1条边
            //每一次生成的子图和哪个结点的距离最近
            for (int i = 0; i < graph.verxs; i++) {//i为访问过的结点
                for (int j = 0; j < graph.verxs; j++) {//j为未访问的结点
                    if(visited[i]==1&&visited[j]==0&& graph.weight[i][j]<minweight)
                    {
                        minweight=graph.weight[i][j];
                        h1=i;
                        h2=j;
                    }
                }
            }
            System.out.println("边<"+h1+","+h2+">"+"权值为:"+minweight);
            primepoints[pp]=graph.data[h1];
            pp++;
            primepoints[pp]=graph.data[h2];
            pp++;
            visited[h2]=1;
            minweight=100;
        }
        return primepoints;
    }
}

class MGraph{
    int verxs;//图的节点数
    char[] data;//结点数据
    int[][] weight;//邻接矩阵
    public MGraph(int verxs){
        this.verxs=verxs;
        data=new char[verxs];
        weight=new int[verxs][verxs];
    }
}
