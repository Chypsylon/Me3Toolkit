/*
 * The MIT License
 *
 * Copyright 2016 Chypsylon <therealchypsylon@gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package eu.chypsylon.me3toolkit.ui;

import javax.swing.JButton;
import javax.swing.JSpinner;

/**
 *
 * @author Chypsylon <therealchypsylon@gmail.com>
 */
public class FovFixPanel extends javax.swing.JPanel {

    /**
     * Creates new form FovFixPanel
     */
    public FovFixPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fovDescriptionLabel = new javax.swing.JLabel();
        fovValueLabel = new javax.swing.JLabel();
        fovSpinner = new javax.swing.JSpinner();
        applyFovButton = new javax.swing.JButton();

        fovDescriptionLabel.setText("<html>\nHere you can change the Field of View (FOV). The default value is 70, I recommend a value between 90 and 110. You'll have to right-click once at each game start to activate the changed FOV.\n</html>");
        fovDescriptionLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        fovValueLabel.setText("New FOV Value:");

        fovSpinner.setModel(new javax.swing.SpinnerNumberModel(70, 0, 512, 1));

        applyFovButton.setText("Apply FOV");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fovDescriptionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(fovValueLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fovSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(applyFovButton)
                        .addGap(0, 197, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fovDescriptionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fovValueLabel)
                    .addComponent(fovSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(applyFovButton))
                .addContainerGap(194, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    public JButton getApplyFovButton() {
        return applyFovButton;
    }

    public JSpinner getFovSpinner() {
        return fovSpinner;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton applyFovButton;
    private javax.swing.JLabel fovDescriptionLabel;
    private javax.swing.JSpinner fovSpinner;
    private javax.swing.JLabel fovValueLabel;
    // End of variables declaration//GEN-END:variables
}