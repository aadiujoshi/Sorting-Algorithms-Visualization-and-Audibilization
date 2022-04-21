import java.util.*;

public class Algorithms 
{
    private double[][] audioSet; //first index of audio set tells the frequency
    private Window window;
    
    public Algorithms() //audioSet defaults to 100 
    {
        audioSet = new double[200][400]; 
        window = new Window();
        
        int index = 0;
        for(int i = 200; i < 2200; i+=10)
        {
            audioSet[index] = Sound.pureTone(i, 0.05);
            index++;
        }
        
        try
        {
            Thread.sleep(250);
        }
        catch (InterruptedException ie)
        {
            ie.printStackTrace();
        }
    }
    
    public void shuffleAudioSet()
    {
        Collections.shuffle(Arrays.asList(audioSet));
    }
    
    public void selectionSort()
    {
        shuffleAudioSet();
        window.drawAudioSet(audioSet);
        
        for(int j = 0; j < audioSet.length; j++)
        {
            for(int i = audioSet.length-1; i >= 1; i--)
            {
                if(audioSet[i-1][0] > audioSet[i][0])
                {
                    double[] temp = audioSet[i];
                    audioSet[i] = audioSet[i-1];
                    audioSet[i-1] = temp;
                    Sound.play(audioSet[i]);
                    
                    window.drawFrequency((int)audioSet[i][0], i);
                    window.drawFrequency((int)audioSet[i-1][0], i-1);
                }
            }
        }
        
        Sound.playAudioSet(audioSet);
    }
    
    public void insertionSort() 
    {
        shuffleAudioSet();
        window.drawAudioSet(audioSet);
        
        for(int i = 1; i < audioSet.length; i++)
        {
            int safeI = i;
            
            while(i != 0 && audioSet[i][0] < audioSet[i-1][0])
            {
                double[] temp = audioSet[i];
                audioSet[i] = audioSet[i-1];
                audioSet[i-1] = temp;
                i--;
            }
            
            Sound.play(audioSet[i]);
            window.drawAudioSet(audioSet);
            i = safeI;
        }
        
        Sound.playAudioSet(audioSet);
    }
    
    public void recursiveMergeSort()
    {
        shuffleAudioSet();
        window.drawAudioSet(audioSet);
        
        mSort(0, audioSet.length);
        
        Sound.playAudioSet(audioSet);
    }
    
    //PART OF RECMERGESORT
    public void merge(int l, int m, int r)
    {
        double[][] combinedArr = new double[r-l][audioSet[0].length];
        
        int lt = l;
        int rt = m;
        int index = 0;
        
        while(lt < m && rt < r)
        {
            if(audioSet[rt][0] < audioSet[lt][0])
            {
                combinedArr[index] = audioSet[rt];
                rt++;
            }
            else
            {
                combinedArr[index] = audioSet[lt];
                lt++;
            }
            index++;
        }
        
        while(lt < m)
        {
            combinedArr[index] = audioSet[lt];
            index++;
            lt++;
        }
        
        while(rt < r)
        {
            combinedArr[index] = audioSet[rt];
            index++;
            rt++;
        }
        
        for(int i = 0; i < combinedArr.length; i++)
        {
            audioSet[i+l] = combinedArr[i];
            
            Sound.play(audioSet[i]);
            window.drawAudioSet(audioSet);
        }
    }
    
    //PART OF RECMERGESORT
    public void mSort(int l, int r) //exclusive
    {
        if(l+1 < r)
        {
            int mid = (int)(l+r)/2;
            
            mSort(l, mid);
            mSort(mid, r);
            merge(l, mid, r);
        }
    }
    
    public void quickSort()
    {
        shuffleAudioSet();
        window.drawAudioSet(audioSet);
        
        //audioSet = new double[][]{{3},{8},{6},{7},{2},{4},{5},{1}};
        
        qSort(0, audioSet.length-1);
        
        Sound.playAudioSet(audioSet);
    }
    
    //PART OF QUICKSORT
    public void qSort(int l, int r)
    {
        int base = (int)audioSet[l][0]; //first frequency
        
        int lc = l+1;
        int rc = r; //inclusive
        
        while(lc < rc )
        {
            while(lc+1 < audioSet.length && audioSet[lc][0] < base)
                lc++;
            while(rc-1 < audioSet.length && audioSet[rc][0] > base)
                rc--;
            if(lc > rc)
                break;
            
            double[] temp = audioSet[lc];
            audioSet[lc] = audioSet[rc];
            audioSet[rc] = temp;
            
            Sound.play(audioSet[lc]);
            Sound.play(audioSet[rc]);
            
            window.drawAudioSet(audioSet);
            window.drawAudioSet(audioSet);
        }
        
        double[] temp = audioSet[l];
        audioSet[l] = audioSet[rc];
        audioSet[rc] = temp;
        
        if(l < r && rc-1 > 0 && lc < 200)
        {
            qSort(l, rc-1);
            qSort(lc, r);
        }
    }
    
    public void radixLSDSort()
    {
        shuffleAudioSet();
        window.drawAudioSet(audioSet);
        
        double[][][] radixValues = new double[10][audioSet.length][audioSet[0].length];
        int[] nextFree = new int[10]; 
        
        int maxPlace = 1;
        int minPlace = 10;
        
        for(int i = 0; i < audioSet.length; i++)
        {
            while(((int)audioSet[i][0]%(maxPlace*10))/maxPlace == 0 && 
            audioSet[i][0] > maxPlace)
            {
                maxPlace*=10;
            }
        }
        
        //ACTUAL SORTING
        for(int placeVal = minPlace; placeVal <= maxPlace; placeVal*=10)
        {
            for(int i = 0; i < audioSet.length; i++) //populate radix values with placeValue
            {
                int radixVal = ((int)audioSet[i][0]%(placeVal*10))/placeVal;
                radixValues[radixVal][nextFree[radixVal]] = audioSet[i];
                nextFree[radixVal]++;
            }
            int index = 0;
            
            for(int radix = 0; radix < radixValues.length; radix++)
            {
                for(int value = 0; value < nextFree[radix]; value++)
                {
                    audioSet[index] = radixValues[radix][value];
                    
                    Sound.play(audioSet[index]);
                    window.drawAudioSet(audioSet);
                    //window.drawFrequency((int)audioSet[index][0], index);
                    
                    index++;
                }
            }
            
            Arrays.fill(nextFree, 0);
        }
        
        Sound.playAudioSet(audioSet);
    }
    
    public void cocktailShakerSort()
    {
        shuffleAudioSet();
        window.drawAudioSet(audioSet);
        
        boolean forw = true;
        boolean backw = false;
        
        int dec = 0;
        
        for(;;)
        {
            if(forw == true)
            {
                forw = false;
                backw = true;
                
                for(int i = dec; i < audioSet.length-dec-1; i++)
                {
                    if(audioSet[i+1][0] < audioSet[i][0])
                    {
                        double[] temp = audioSet[i];
                        audioSet[i] = audioSet[i+1];
                        audioSet[i+1] = temp;
                        
                        Sound.play(audioSet[i]);
                        window.drawFrequency((int)audioSet[i][0], i);
                        window.drawFrequency((int)audioSet[i+1][0], i+1);
                    }
                }
            }
            else if(backw == true)
            {
                backw = false;
                forw = true;
                
                for(int i = audioSet.length-dec-1; i > dec; i--)
                {
                    if(audioSet[i-1][0] > audioSet[i][0])
                    {
                        double[] temp = audioSet[i];
                        audioSet[i] = audioSet[i-1];
                        audioSet[i-1] = temp;
                        
                        Sound.play(audioSet[i]);
                        window.drawFrequency((int)audioSet[i][0], i);
                        window.drawFrequency((int)audioSet[i-1][0], i-1);
                    }
                }
                
                dec++;
            }
            if(dec == audioSet.length/3) {break;}
        }
        
        Sound.playAudioSet(audioSet);
    }
    
    public void smallestSort() //(i think)orginal sorting algorithm
    {
        shuffleAudioSet();
        window.drawAudioSet(audioSet);
        
        for(int j = 0; j < audioSet.length; j++)
        {
            int smallest = j;
            for(int i = j; i < audioSet.length; i++)
            {
                if(audioSet[i][0] < audioSet[smallest][0])
                {
                    smallest = i;
                }
            }
            
            double[] temp = audioSet[j];
            audioSet[j] = audioSet[smallest];
            audioSet[smallest] = temp;
            
            Sound.play(audioSet[smallest]);
            window.drawAudioSet(audioSet);
        }
        
        Sound.playAudioSet(audioSet);
    }
    
    public void bubbleSort()
    {
        shuffleAudioSet();
        window.drawAudioSet(audioSet);
        
        for(int j = 0; j < audioSet.length; j++)
        {
            for(int i = 0; i < audioSet.length-1; i++)
            {
                while(audioSet[i+1][0] < audioSet[i][0])
                {
                    double[] temp = audioSet[i];
                    audioSet[i] = audioSet[i+1];
                    audioSet[i+1] = temp;
                    
                    Sound.play(audioSet[i]);
                    window.drawFrequency((int)audioSet[i][0], i);
                    window.drawFrequency((int)audioSet[i+1][0], i+1);
                }
            }
        }
        
        Sound.playAudioSet(audioSet);
    }
}