package org.quickstep;

import javax.swing.*;
import javax.swing.border.Border;

public interface ComponentFactory
{
   JComponent createLabel(String text);

   JComponent createHeader(String title, boolean first);

   JComponent createSeparator(GridBagToolKit.Orientation orientation);

   JPanel createPanel();

   Border createBorder(String title);

   JScrollPane createScrollPane();

   GridBagBuilder createGridBagBuilder(JPanel gridContainer, GridSpec gridSpec, ComponentFactory factory);

   ComponentFactory getChildFactory();
}
