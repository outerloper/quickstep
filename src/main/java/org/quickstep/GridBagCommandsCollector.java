package org.quickstep;

import javax.swing.*;

interface GridBagCommandsCollector<T extends GridBagCommandsCollector<T>>
{
   T addLineBreak();

   T addBlank();

   T addBlank(CellSpec spec);

   T add(String text);

   T add(String text, CellSpec spec);

   T add(JComponent component);

   T add(JComponent component, CellSpec spec);

   T addAll(Iterable<? extends JComponent> components);

   T addAll(Iterable<? extends JComponent> components, CellSpec spec);

   T addHeader(String title);

   T addVerticalSeparator();

   T addHorizontalSeparator();

   T add(GridBagCommand command);
}
