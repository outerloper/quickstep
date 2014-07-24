package org.quickstep;

import javax.swing.*;

interface GridBagCommandsCollector<T extends GridBagCommandsCollector<T>>
{
   T nextLine();

   T addBlank();

   T addBlank(GridBagSpec spec);

   T add(String text);

   T add(String text, GridBagSpec spec);

   T add(JComponent component);

   T add(JComponent component, GridBagSpec spec);

   T addAll(Iterable<? extends JComponent> components);

   T addAll(Iterable<? extends JComponent> components, GridBagSpec spec);

   T addHeader(String title);

   T addSeparator();

   T addSeparatingLine();

   T add(GridBagCommand command);
}
