/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JDialogHideArea.java
 *
 * Created on 2009-12-8, 15:13:56
 */

/**
 *
 * @author Administrator
 */

package com.substation.hk_sdk;

import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.examples.win32.GDI32.RECT;
import com.sun.jna.examples.win32.W32API;
import com.sun.jna.examples.win32.W32API.HANDLE;
import com.sun.jna.examples.win32.W32API.HWND;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/*****************************************************************************
 *类 ：JDialogHideArea
 *类描述 ：遮挡区域设置
 ****************************************************************************/
public class JDialogHideArea extends JDialog {

    static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
    static GDI32 gDi = GDI32.INSTANCE;
    static USER32 uSer = USER32.INSTANCE;

    NativeLong m_lUserID;//用户ID
    int	m_iChanShowNum;//父窗口传来的通道号,预览此通道
    NativeLong m_lPlayHandle;//预览句柄

    FDrawFunSet ShelterAreaSetCallBack;//设置遮挡区域的回调函数
    FDrawFunGet ShelterAreaGetCallBack;//显示遮挡区域的回调函数

    HCNetSDK.NET_DVR_CLIENTINFO m_strClientInfo;//预览参数
    HCNetSDK.NET_DVR_PICCFG_V30 m_struPicCfg;//主窗口传来的图像参数结构体指针

     boolean m_bDrawdetect;
     int g_iHideAreaCount = 4;//mask area number
     RECT[] g_rectHideAreaShow = new RECT[HCNetSDK.MAX_SHELTERNUM];//display mask area
     RECT[] g_rectHideAreaMouse = new RECT[HCNetSDK.MAX_SHELTERNUM];//set mask area


    /*************************************************
    函数:      JDialogHideArea
    函数描述:  构造函数   Creates new form JDialogHideArea
     *************************************************/
    public JDialogHideArea(JDialog parent, boolean modal, NativeLong lUserID, int iChannelNum, HCNetSDK.NET_DVR_PICCFG_V30 struPicCfg)
    {
        super(parent, modal);
        initComponents();
        m_lUserID = lUserID;
        m_iChanShowNum = iChannelNum;
        m_struPicCfg = struPicCfg;
        m_lPlayHandle = new NativeLong(-1);
        ShelterAreaSetCallBack = new FDrawFunSet();
        ShelterAreaGetCallBack = new FDrawFunGet();
        initialDialog();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPlay = new Panel();
        jButtonSet = new JButton();
        jButtonExit = new JButton();
        jRadioButtonShowArea = new JRadioButton();
        jRadioButtonSetArea = new JRadioButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("遮挡区域配置");

        panelPlay.setBackground(new Color(204, 255, 255));
        panelPlay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelPlayMousePressed(evt);
            }
        });
        panelPlay.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                panelPlayMouseDragged(evt);
            }
        });
        panelPlay.setLayout(null);

        jButtonSet.setText("设置");
        jButtonSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSetActionPerformed(evt);
            }
        });

        jButtonExit.setText("退出");
        jButtonExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExitActionPerformed(evt);
            }
        });

        jRadioButtonShowArea.setText("显示遮盖区域");
        jRadioButtonShowArea.setEnabled(false);
        jRadioButtonShowArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonShowAreaActionPerformed(evt);
            }
        });

        jRadioButtonSetArea.setText("设置遮盖区域");
        jRadioButtonSetArea.setEnabled(false);
        jRadioButtonSetArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonSetAreaActionPerformed(evt);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jRadioButtonShowArea)
                        .addGap(81, 81, 81)
                        .addComponent(jRadioButtonSetArea))
                    .addComponent(panelPlay, GroupLayout.PREFERRED_SIZE, 352, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jButtonSet)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 169, Short.MAX_VALUE)
                .addComponent(jButtonExit)
                .addGap(63, 63, 63))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelPlay, GroupLayout.PREFERRED_SIZE, 288, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButtonShowArea)
                    .addComponent(jRadioButtonSetArea))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSet)
                    .addComponent(jButtonExit))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*************************************************
    函数:      "设置"  按钮响应函数
    函数描述:   保存遮挡区域设置到结构体
     *************************************************/
    private void jButtonSetActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonSetActionPerformed
    {//GEN-HEADEREND:event_jButtonSetActionPerformed
       	int k = 0;
	for (k = 0; k < HCNetSDK.MAX_SHELTERNUM; k++)
	{
		m_struPicCfg.struShelter[k].wHideAreaTopLeftX = 0;
		m_struPicCfg.struShelter[k].wHideAreaTopLeftY = 0;
		m_struPicCfg.struShelter[k].wHideAreaWidth = 0;
		m_struPicCfg.struShelter[k].wHideAreaHeight = 0;
	}
	for (k = 0; k<g_iHideAreaCount; k++)
	{
		if (g_rectHideAreaMouse[k].top <= g_rectHideAreaMouse[k].bottom)
		{
			if (g_rectHideAreaMouse[k].left <= g_rectHideAreaMouse[k].right)
			{
				m_struPicCfg.struShelter[k].wHideAreaTopLeftX = (short)(g_rectHideAreaMouse[k].left*2);
				m_struPicCfg.struShelter[k].wHideAreaTopLeftY = (short)(g_rectHideAreaMouse[k].top*2);
				m_struPicCfg.struShelter[k].wHideAreaWidth = (short)((g_rectHideAreaMouse[k].right-g_rectHideAreaMouse[k].left)*2);
				m_struPicCfg.struShelter[k].wHideAreaHeight = (short)((g_rectHideAreaMouse[k].bottom-g_rectHideAreaMouse[k].top)*2);
			}
			else
			{
				m_struPicCfg.struShelter[k].wHideAreaTopLeftX = (short)(g_rectHideAreaMouse[k].right*2);
				m_struPicCfg.struShelter[k].wHideAreaTopLeftY = (short)(g_rectHideAreaMouse[k].top*2);
				m_struPicCfg.struShelter[k].wHideAreaWidth = (short)((g_rectHideAreaMouse[k].left-g_rectHideAreaMouse[k].right)*2);
				m_struPicCfg.struShelter[k].wHideAreaHeight = (short)((g_rectHideAreaMouse[k].bottom-g_rectHideAreaMouse[k].top)*2);
			}
		}
		else
		{
			if (g_rectHideAreaMouse[k].left <= g_rectHideAreaMouse[k].right)
			{
				m_struPicCfg.struShelter[k].wHideAreaTopLeftX = (short)(g_rectHideAreaMouse[k].left*2);
				m_struPicCfg.struShelter[k].wHideAreaTopLeftY = (short)(g_rectHideAreaMouse[k].bottom*2);
				m_struPicCfg.struShelter[k].wHideAreaWidth = (short)((g_rectHideAreaMouse[k].right-g_rectHideAreaMouse[k].left)*2);
				m_struPicCfg.struShelter[k].wHideAreaHeight = (short)((g_rectHideAreaMouse[k].top-g_rectHideAreaMouse[k].bottom)*2);
			}
			else
			{
				m_struPicCfg.struShelter[k].wHideAreaTopLeftX = (short)(g_rectHideAreaMouse[k].right*2);
				m_struPicCfg.struShelter[k].wHideAreaTopLeftY = (short)(g_rectHideAreaMouse[k].bottom*2);
				m_struPicCfg.struShelter[k].wHideAreaWidth = (short)((g_rectHideAreaMouse[k].left-g_rectHideAreaMouse[k].right)*2);
				m_struPicCfg.struShelter[k].wHideAreaHeight = (short)((g_rectHideAreaMouse[k].top-g_rectHideAreaMouse[k].bottom)*2);
			}
		}
	}
    }//GEN-LAST:event_jButtonSetActionPerformed

    /*************************************************
    函数:      "显示遮盖区域"  按钮响应函数
    函数描述:   保存遮挡区域设置到结构体
     *************************************************/
    private void jRadioButtonShowAreaActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jRadioButtonShowAreaActionPerformed
    {//GEN-HEADEREND:event_jRadioButtonShowAreaActionPerformed
       if (m_lPlayHandle.intValue() < 0)
		return;
	if (jRadioButtonSetArea.isSelected())
	{
		jRadioButtonSetArea.setSelected(false);
		m_bDrawdetect = false;
		jButtonSet.setEnabled(false);
	}
	if (jRadioButtonShowArea.isSelected())
	{
		hCNetSDK.NET_DVR_RigisterDrawFun(m_lPlayHandle,null, 0);

            try
            {
                Thread.sleep(200);
            } catch (InterruptedException ex)
            {
                Logger.getLogger(JDialogHideArea.class.getName()).log(Level.SEVERE, null, ex);
            }

		hCNetSDK.NET_DVR_RigisterDrawFun(m_lPlayHandle,ShelterAreaGetCallBack, 0);
	}
	else
	{
		hCNetSDK.NET_DVR_RigisterDrawFun(m_lPlayHandle,null, 0);
	}
    }//GEN-LAST:event_jRadioButtonShowAreaActionPerformed

    /*************************************************
    函数:      "设置遮盖区域"  按钮响应函数
    函数描述:   根据鼠标移动显示要设置的遮挡区域
     *************************************************/
    private void jRadioButtonSetAreaActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jRadioButtonSetAreaActionPerformed
    {//GEN-HEADEREND:event_jRadioButtonSetAreaActionPerformed
       	if (m_lPlayHandle.intValue() < 0)
		return;

        	if (jRadioButtonShowArea.isSelected())
	{
		jRadioButtonShowArea.setSelected(false);
	}
	if (jRadioButtonSetArea.isSelected())
	{
		hCNetSDK.NET_DVR_RigisterDrawFun(m_lPlayHandle,null,0);
		m_bDrawdetect = true;
		g_iHideAreaCount = 0;
		for (int i=0; i < HCNetSDK.MAX_SHELTERNUM; i++)
		{
			g_rectHideAreaMouse[i].left = 0;
			g_rectHideAreaMouse[i].top = 0;
			g_rectHideAreaMouse[i].bottom = 0;
			g_rectHideAreaMouse[i].right = 0;
		}
		jButtonSet.setEnabled(true);
	}
	else
	{
		m_bDrawdetect = false;
		hCNetSDK.NET_DVR_RigisterDrawFun(m_lPlayHandle,null,0);
		jButtonSet.setEnabled(false);
	}
    }//GEN-LAST:event_jRadioButtonSetAreaActionPerformed

    /*************************************************
    函数:      "退出"  单击
    函数描述:   销毁对话框
     *************************************************/
    private void jButtonExitActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonExitActionPerformed
    {//GEN-HEADEREND:event_jButtonExitActionPerformed
       	if (m_lPlayHandle.intValue() >= 0)
	{
		hCNetSDK.NET_DVR_RigisterDrawFun(m_lPlayHandle, null, 0);
		hCNetSDK.NET_DVR_StopRealPlay(m_lPlayHandle);
		m_lPlayHandle.setValue(-1);
	}
        dispose();
    }//GEN-LAST:event_jButtonExitActionPerformed

    /*************************************************
    函数:      "panelPlay"  单击响应函数
    函数描述:   保存单击处点的坐标
     *************************************************/
    private void panelPlayMousePressed(java.awt.event.MouseEvent evt)//GEN-FIRST:event_panelPlayMousePressed
    {//GEN-HEADEREND:event_panelPlayMousePressed
       	if (m_bDrawdetect)
	{
            Point point = new Point(evt.getX(), evt.getY());
			hCNetSDK.NET_DVR_RigisterDrawFun(m_lPlayHandle, ShelterAreaSetCallBack, 0);
			if (g_iHideAreaCount >= HCNetSDK.MAX_SHELTERNUM)
				g_iHideAreaCount = 0;
			if (point.x < 0)
				point.x = 0;
			g_rectHideAreaMouse[g_iHideAreaCount].left = point.x/16*16;
			if (point.y < 0)
				point.y = 0;
			g_rectHideAreaMouse[g_iHideAreaCount].top = point.y/16*16;
			g_rectHideAreaMouse[g_iHideAreaCount].right = g_rectHideAreaMouse[g_iHideAreaCount].left;
			g_rectHideAreaMouse[g_iHideAreaCount].bottom = g_rectHideAreaMouse[g_iHideAreaCount].top;
			g_rectHideAreaShow[g_iHideAreaCount].left = point.x/16*16;
			g_rectHideAreaShow[g_iHideAreaCount].top = point.y/16*16;
			g_rectHideAreaShow[g_iHideAreaCount].right = point.x/16*16 + 1;
			g_rectHideAreaShow[g_iHideAreaCount].bottom = point.y/16*16 + 1;
			g_iHideAreaCount ++;
	}
    }//GEN-LAST:event_panelPlayMousePressed

    /*************************************************
    函数:      "panelPlay"  鼠标按下移动  时间响应函数
    函数描述:   保存移动点坐标并回调画框
     *************************************************/
    private void panelPlayMouseDragged(java.awt.event.MouseEvent evt)//GEN-FIRST:event_panelPlayMouseDragged
    {//GEN-HEADEREND:event_panelPlayMouseDragged
        if (m_bDrawdetect)
        {
            Point point = new Point(evt.getX(), evt.getY());
            if (point.x > 352)
            {
                point.x = 352;
            }
            g_rectHideAreaMouse[g_iHideAreaCount - 1].right = point.x / 16 * 16;
            if (point.y > 288)
            {
                point.y = 288;
            }

            g_rectHideAreaMouse[g_iHideAreaCount - 1].bottom = point.y / 16 * 16;
            g_rectHideAreaShow[g_iHideAreaCount - 1].right = point.x / 16 * 16;
            g_rectHideAreaShow[g_iHideAreaCount - 1].bottom = point.y / 16 * 16;
        }
    }//GEN-LAST:event_panelPlayMouseDragged

     /*************************************************
    函数:      initialDialog
    函数描述:   初始化对话框
     *************************************************/
private void initialDialog()
{
    	int i;
	for (i=0; i < HCNetSDK.MAX_SHELTERNUM; i++)
	{
                g_rectHideAreaMouse[i] = new RECT();
                g_rectHideAreaShow[i] = new RECT();
		g_rectHideAreaMouse[i].left = m_struPicCfg.struShelter[i].wHideAreaTopLeftX/2;
		g_rectHideAreaMouse[i].top =  m_struPicCfg.struShelter[i].wHideAreaTopLeftY/2;
		g_rectHideAreaMouse[i].bottom = ( m_struPicCfg.struShelter[i].wHideAreaTopLeftY + m_struPicCfg.struShelter[i].wHideAreaHeight)/2;
		g_rectHideAreaMouse[i].right = ( m_struPicCfg.struShelter[i].wHideAreaTopLeftX + m_struPicCfg.struShelter[i].wHideAreaWidth)/2;
	}

       HWND hwnd = new HWND(Native.getComponentPointer(panelPlay));
        m_strClientInfo = new HCNetSDK.NET_DVR_CLIENTINFO();
        m_strClientInfo.lChannel = new NativeLong(m_iChanShowNum);
        m_strClientInfo.hPlayWnd = hwnd;
        m_lPlayHandle = hCNetSDK.NET_DVR_RealPlay_V30(m_lUserID,
                m_strClientInfo, null, null, true);
        if(m_lPlayHandle.intValue() == -1)
        {
            JOptionPane.showMessageDialog(this, "预览失败,错误值:" + hCNetSDK.NET_DVR_GetLastError());
        }

        m_bDrawdetect = false;
        jRadioButtonShowArea.setEnabled(true);
        jRadioButtonSetArea.setEnabled(true);
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton jButtonExit;
    private JButton jButtonSet;
    private JRadioButton jRadioButtonSetArea;
    private JRadioButton jRadioButtonShowArea;
    private Panel panelPlay;
    // End of variables declaration//GEN-END:variables

     /*************************************************
    类:        FDrawFunSet
    函数描述:   设置遮挡区域回调函数
     *************************************************/
    class FDrawFunSet implements HCNetSDK.FDrawFun
    {
        public void invoke(NativeLong lRealHandle, W32API.HDC hDc, int dwUser)
        {
          	for (int i=0; i < g_iHideAreaCount; i++)
	{
		uSer.DrawEdge(hDc,g_rectHideAreaShow[i],USER32.BDR_SUNKENOUTER,USER32.BF_RECT);
	}
            gDi.SetBkMode(hDc,GDI32.TRANSPARENT);
        }
    }

     /*************************************************
    类:        FDrawFunGet
    函数描述:   显示遮挡区域回调函数
     *************************************************/
        class FDrawFunGet implements HCNetSDK.FDrawFun
    {
        public void invoke(NativeLong lRealHandle, W32API.HDC hDc, int dwUser)
        {

        RECT rect = new RECT();
	int i = 0;
	//CPoint point;
        HANDLE hBrush = gDi.CreateSolidBrush(0xffffff);

	for (i=0; i<g_iHideAreaCount; i++)
	{
		rect.left = g_rectHideAreaMouse[i].left;
		rect.top = g_rectHideAreaMouse[i].top;
		rect.right = g_rectHideAreaMouse[i].right;
		rect.bottom = g_rectHideAreaMouse[i].bottom;
		uSer.FillRect(hDc, rect, hBrush);
	}
	gDi.SetBkMode(hDc,GDI32.TRANSPARENT);
        }
    }
}
