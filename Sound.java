import javax.sound.sampled.*;
public class Sound
{
    public static final double SAMPLES_PER_SECOND = 44100;

    private static final AudioFormat FORMAT = new AudioFormat((float)SAMPLES_PER_SECOND, 8, 1, true, true);
    
    public static void play(double[] samples)
    {
        byte[] data = new byte[samples.length];
        for (int i = 0; i < samples.length; i++)
        {
            int intVal = (int)(127 * samples[i]);
            if (intVal > 127)
                intVal = 127;
            if (intVal < -127)
                intVal = -127;
            data[i] = (byte)intVal;
        }
        Clip clip;
        DataLine.Info info = new DataLine.Info(Clip.class, FORMAT);
        if (!AudioSystem.isLineSupported(info))
            throw new RuntimeException("not supported");
        try
        {
            clip = (Clip)AudioSystem.getLine(info);
            clip.open(FORMAT, data, 0, data.length);
            clip.start();
            clip.drain();
        }
        catch(LineUnavailableException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    public static void playAudioSet(double[][] audioSet)
    {
        for(int i = 1; i < audioSet.length; i++)
        {
            play(audioSet[i]);
            try
            {
                Thread.sleep(5);
            }
            catch (InterruptedException ie)
            {
                ie.printStackTrace();
            }
        }
    }
    
    public static double[] pureTone(double freq, double seconds)
    {
        double[] samples = new double[(int)(seconds * SAMPLES_PER_SECOND)];
        samples[0] = freq;
        for (int i = 1; i < samples.length; i++)
            samples[i] = Math.sin(2 * Math.PI * i * freq / SAMPLES_PER_SECOND);
        return samples;
    }
}