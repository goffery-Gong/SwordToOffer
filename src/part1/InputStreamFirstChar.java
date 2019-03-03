package part1;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/3/1 10:25
 * @Description:
 */
public class InputStreamFirstChar {
    private int index;
    private int[] map=new int[256];//将chr的ascll码作为key，下标值index作为value

    public InputStreamFirstChar(){
        for(int i=0; i<256; i++)
            map[i]=-1;
    }

    public void Insert(char ch)
    {
        if(map[ch]==-1)
            map[ch]=index;
        else if(map[ch]>=0)
            map[ch]=-2;
        index++;
    }
  //return the first appearence once char in current stringstream
    public char FirstAppearingOnce()
    {
        int min=Integer.MAX_VALUE;
        char c='#';
        for(int i=0; i<256; i++){
            if(map[i]>=0 && map[i]<min){
                c=(char) i;
                min=map[i];
            }
        }
         return c;
    }
}
