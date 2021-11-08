/*                              *
 *   Petra2                     *
 *   Van de Weerdt Tom 2325     *
 *   Voneche Lenny 2325         *
 *                              */  
package petraudp;
import java.awt.Color;
import java.io.IOException;
public class CapteurThread extends Thread{
    
    private petraUI ui;
    private int etat=0;
    
    public CapteurThread(petraUI uitemp) 
    {
        this.ui = uitemp;
    }
    
    @Override
    public void run() 
    {
       while(true)
       {
            try 
            {
                
                    while ((etat=ui.dis.read()) == 0);
            
                    switch(etat)
                    {
                        case 10 : 
                            //L1
                            ui.C_L1.setText("OFF"); 
                            ui.C_L1.setForeground(Color.red);  
                            break; 
                        case 11 : 
                            ui.C_L1.setText("ON");  
                            ui.C_L1.setForeground(Color.green);
                            break;
                                    
                        case 20 : 
                            //L2
                            ui.C_L2.setText("OFF"); 
                            ui.C_L2.setForeground(Color.red);  
                            break; 
                            
                        case 21 : 
                            ui.C_L2.setText("ON");  
                            ui.C_L2.setForeground(Color.green);
                            break;
                        
                        case 30 : 
                            //S
                            ui.C_S.setText("OFF"); 
                            ui.C_S.setForeground(Color.red);    
                            break; 
                            
                        case 31 : 
                            ui.C_S.setText("ON");  
                            ui.C_S.setForeground(Color.green);  
                            break;
                        
                        case 40 : 
                            //CS   
                            ui.C_CS.setText("OFF"); 
                            ui.C_CS.setForeground(Color.red);  
                            break; 
                            
                        case 41 : 
                            ui.C_CS.setText("ON");
                            ui.C_CS.setForeground(Color.green);
                            break;
                        
                        case 50 : 
                            //AP
                            ui.C_AP.setText("OFF");
                            ui.C_AP.setForeground(Color.red);
                            break; 
                            
                        case 51 : 
                            ui.C_AP.setText("ON");
                            ui.C_AP.setForeground(Color.green);
                            break;
                        
                        case 60 : 
                            //PP
                            ui.C_PP.setText("OFF"); 
                            ui.C_PP.setForeground(Color.red);  
                            break; 
                            
                        case 61 : 
                            ui.C_PP.setText("ON");
                            ui.C_PP.setForeground(Color.green);
                            break;
                        
                        case 70 : 
                            //DE
                            ui.C_DE.setText("OFF"); 
                            ui.C_DE.setForeground(Color.red);  
                            break; 
                            
                        case 71 : 
                            ui.C_DE.setText("ON");
                            ui.C_DE.setForeground(Color.green);
                            break;
                        
                        case 80 : 
                            ui.C_T.setText("OFF");
                            ui.C_T.setForeground(Color.red);
                            break;
                            
                        case 81 : 
                            ui.C_T.setText("ON");
                            ui.C_T.setForeground(Color.green);
                            break;
                    }              
                        
            }
            catch (IOException ex)
            {
                System.err.println("Err : "+ex);
            } 
        }
    }
    
}
