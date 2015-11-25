import java.awt.AWTEventMulticaster;import java.awt.event.ActionEvent;import java.awt.event.ActionListener;import java.awt.event.MouseListener;import java.awt.event.MouseEvent;import java.awt.Point;import java.util.Hashtable;import quicktime.QTException;import quicktime.app.anim.Compositor;import quicktime.app.anim.TwoDSprite;import quicktime.app.event.QTMouseTargetController;import quicktime.app.event.MouseButtonListener;import quicktime.app.event.MouseTargetAdapter;import quicktime.app.event.QTMouseEvent;import quicktime.qd.QDColor;import quicktime.qd.QDConstants;import quicktime.std.image.GraphicsMode;/** * QTZoo Module 8 - Using QuickTime Transitions  * The pane that specifies common behavior for all of the panes in the zoo project * * @author Levi Brown * @author Michael Hopkins * @author Apple Computer, Inc. * @version 8.0 4/10/2000 * Copyright: 	� Copyright 1999 Apple Computer, Inc. All rights reserved. *	 * Disclaimer:	IMPORTANT:  This Apple software is supplied to you by Apple Computer, Inc. *				("Apple") in consideration of your agreement to the following terms, and your *				use, installation, modification or redistribution of this Apple software *				constitutes acceptance of these terms.  If you do not agree with these terms, *				please do not use, install, modify or redistribute this Apple software. * *				In consideration of your agreement to abide by the following terms, and subject *				to these terms, Apple grants you a personal, non-exclusive license, under Apple�s *				copyrights in this original Apple software (the "Apple Software"), to use, *				reproduce, modify and redistribute the Apple Software, with or without *				modifications, in source and/or binary forms; provided that if you redistribute *				the Apple Software in its entirety and without modifications, you must retain *				this notice and the following text and disclaimers in all such redistributions of *				the Apple Software.  Neither the name, trademarks, service marks or logos of *				Apple Computer, Inc. may be used to endorse or promote products derived from the *				Apple Software without specific prior written permission from Apple.  Except as *				expressly stated in this notice, no other rights or licenses, express or implied, *				are granted by Apple herein, including but not limited to any patent rights that *				may be infringed by your derivative works or by other works in which the Apple *				Software may be incorporated. * *				The Apple Software is provided by Apple on an "AS IS" basis.  APPLE MAKES NO *				WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE IMPLIED *				WARRANTIES OF NON-INFRINGEMENT, MERCHANTABILITY AND FITNESS FOR A PARTICULAR *				PURPOSE, REGARDING THE APPLE SOFTWARE OR ITS USE AND OPERATION ALONE OR IN *				COMBINATION WITH YOUR PRODUCTS. * *				IN NO EVENT SHALL APPLE BE LIABLE FOR ANY SPECIAL, INDIRECT, INCIDENTAL OR *				CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE *				GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) *				ARISING IN ANY WAY OUT OF THE USE, REPRODUCTION, MODIFICATION AND/OR DISTRIBUTION *				OF THE APPLE SOFTWARE, HOWEVER CAUSED AND WHETHER UNDER THEORY OF CONTRACT, TORT *				(INCLUDING NEGLIGENCE), STRICT LIABILITY OR OTHERWISE, EVEN IF APPLE HAS BEEN *				ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. *  *  */public abstract class ZooPane{	/**	 *	 */	public Compositor getCompositor()	{		return compositor;	}	/**	 * Called to do any setup after being set as the client of the QTCanvas.	 * May be used to start effects running, movies playing, etc.	 */	abstract public void start();	/**	 * Called to do clean up after being removed as the client of the QTCanvas.	 * Should be used to stop effects running, movies playing, etc.	 */	abstract public void stop();		/**     * Sets the command name of the action event fired by this button.     * @param command The name of the action event command fired by this button     */    protected void setActionCommand(String command)    {        actionCommand = command;    }        /**     * Adds the specified action listener to receive action events     * from this button.     * @param l the action listener     */	public void addActionListener(ActionListener l)	{		actionListener = AWTEventMulticaster.add(actionListener, l);	}    /**     * Removes the specified action listener so it no longer receives     * action events from this button.     * @param l the action listener     */	public void removeActionListener(ActionListener l)	{		actionListener = AWTEventMulticaster.remove(actionListener, l);	}	    /**     * Fire an action event to the listeners.     */	protected void fireActionEvent()	{		if (actionListener != null)			actionListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, actionCommand));	}    	public class PaneMouseListener extends MouseTargetAdapter implements MouseButtonListener	{		public PaneMouseListener( QDColor normalBlend, QDColor rollOverBlend, QDColor clickedBlend )		{ 			normalGM   = new GraphicsMode( QDConstants.blend, normalBlend   );			rollOverGM = new GraphicsMode( QDConstants.blend, rollOverBlend );			clickedGM  = new GraphicsMode( QDConstants.blend, clickedBlend  );		}		public void mouseTargetEntered( QTMouseEvent e ) // called when the mouse enters a region managed by the controller		{			isMouseInside = true;			try			{				if ( e.getTarget() instanceof TwoDSprite )				{					TwoDSprite sp = (TwoDSprite) e.getTarget();					if (isMousePressed)						sp.setGraphicsMode( clickedGM );					else						sp.setGraphicsMode( rollOverGM );				}			}			catch (QTException exc)			{					exc.printStackTrace();			}		}				public void mouseTargetExited( QTMouseEvent e ) // called when the mouse exits a region managed by the controller		{			isMouseInside = false;			try			{				if ( e.getTarget() instanceof TwoDSprite )				{					TwoDSprite sp = (TwoDSprite) e.getTarget();					sp.setGraphicsMode( normalGM );				}			}			catch (QTException exc)			{					exc.printStackTrace();			}		}	    public void mouseClicked(QTMouseEvent e) {}		public void mousePressed( QTMouseEvent e )      // called when the mouse is pressed in a region managed by the controller		{			isMousePressed = true;			try			{				if ( e.getTarget() instanceof TwoDSprite )				{					TwoDSprite sp = (TwoDSprite) e.getTarget();					sp.setGraphicsMode( clickedGM );				}			}			catch (QTException exc)			{					exc.printStackTrace();			}		}				public void mouseReleased( QTMouseEvent e )    // called when the mouse is released in a region managed by the controller		{			isMousePressed = false;			try			{				if ( e.getTarget() instanceof TwoDSprite )				{					TwoDSprite sp = (TwoDSprite) e.getTarget();															if (isMouseInside)					{						sp.setGraphicsMode( rollOverGM );  						String area = (String) areas.get(sp);						setActionCommand(area + ";" + e.getX() + ";" + e.getY());												fireActionEvent();					}					else						sp.setGraphicsMode( normalGM );				}			}			catch (QTException exc)			{					exc.printStackTrace();			}		}		GraphicsMode rollOverGM = new GraphicsMode( QDConstants.blend, QDColor.darkGray );		GraphicsMode clickedGM = new GraphicsMode( QDConstants.blend, QDColor.lightGray );		GraphicsMode normalGM;	}		protected Hashtable areas;	protected String actionCommand;	protected ActionListener actionListener = null;	protected Compositor compositor;	protected QTMouseTargetController ctr;	protected boolean isMouseInside;	protected boolean isMousePressed;}