package org.quickstep;

import java.awt.*;
import javax.swing.*;

public interface GridBagBuilderClient
{
   GridBagSpec getSpecAt(int x, int y);

   void addComponent(JComponent component, GridBagConstraints constraints);

   Integer getMaxLineLength();

   boolean isHorizontal();
}
