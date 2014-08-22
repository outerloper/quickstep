package org.quickstep.demo;

import java.util.*;
import java.util.List;
import javax.swing.*;

import org.quickstep.*;

import static org.quickstep.GridBagToolKit.*;

public class CustomCommandsDemo extends JFrame
{
   public CustomCommandsDemo()
   {
      buildContent(this, panel().
         withContentAnchor(A.BOTH).
         withOrientation(Orientation.VERTICAL).
         add(toolbar().
            add(new JButton(UIManager.getIcon("FileChooser.newFolderIcon"))).
            add(new JButton(UIManager.getIcon("FileChooser.upFolderIcon"))).
            addSeparator().
            add(new JButton(UIManager.getIcon("FileChooser.detailsViewIcon"))).
            add(new JButton("Details..."))).
         add(tabs().
            add(tab("First").add("First tab content")).
            add(tab("Second").add("Second tab content")).
            add(tab("Third").add("Third tab content")).
            add(tab("Fourth").add("Fourth tab content")).
            add(tab("Details").
               withIcon(UIManager.getIcon("FileChooser.detailsViewIcon")).
               add("<html><i>Details</i> tab content</html>")
            )
         ).
         add(buttons().
            add(new JButton("OK")).
            add(new JButton("Cancel")).
            add(new JButton("Help"))
         )
      );

      pack();
      setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      setMinimumSize(getPreferredSize());
      setLocationRelativeTo(null);
      setAlwaysOnTop(true);
      setResizable(true);
      setVisible(true);
   }

   public static ToolBarCommand toolbar()
   {
      return new ToolBarCommand();
   }

   public static TabsCommand tabs()
   {
      return new TabsCommand();
   }

   public static TabCommand tab(String title)
   {
      return new TabCommand(title);
   }

   public static ButtonsCommand buttons()
   {
      return new ButtonsCommand();
   }

   public static void main(String[] args) throws Exception
   {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      new CustomCommandsDemo();
   }
}

// TODO rename CellCommand to AbstractComponentBuilder
// TODO parameterize ContainerCommand also with target component type?
class ToolBarCommand extends GridContainerCommand<ToolBarCommand>
{
   @Override
   public JComponent getComponent(Orientation parentOrientation, ComponentFactory parentFactory)
   {
      with(new JToolBar()).withContentAnchorX(AX.LEFT);
      return super.getComponent(parentOrientation, parentFactory);
   }

   @Override
   protected CellSpec getSpec(Orientation parentOrientation)
   {
      return spec().overrideWith(super.getSpec(parentOrientation).withWeightY(0.0));
   }
}


class TabsCommand extends CellCommand<TabsCommand>
{
   private final List<TabCommand> tabs = new LinkedList<TabCommand>();

   @Override
   public JComponent getComponent(Orientation parentOrientation, ComponentFactory parentFactory)
   {
      JTabbedPane result = new JTabbedPane();
      int i = 0;
      for (TabCommand tab : tabs)
      {
         result.add(tab.getTitle(), tab.getComponent());
         if (tab.getIcon() != null)
         {
            result.setIconAt(i, tab.getIcon());
         }
         i++;
      }
      return result;
   }

   public TabsCommand add(TabCommand tab)
   {
      tabs.add(tab);
      return this;
   }
}

class TabCommand extends GridContainerCommand<TabCommand>
{
   private String title;
   private Icon icon;

   public TabCommand(String title)
   {
      this.title = title;
   }

   String getTitle()
   {
      return title;
   }

   Icon getIcon()
   {
      return icon;
   }

   public TabCommand withIcon(Icon icon)
   {
      this.icon = icon;
      return this;
   }
}

class ButtonsCommand extends CellCommand<ButtonsCommand>
{
   List<JButton> buttons = new LinkedList<JButton>();

   public ButtonsCommand add(JButton button)
   {
      buttons.add(button);
      return this;
   }

   // TODO rename parameters to parentOrientation and parentFactory
   @Override
   public JComponent getComponent(Orientation parentOrientation, ComponentFactory parentFactory)
   {
      int maxWidth = 0;
      for (JButton button : buttons)
      {
         int width = button.getPreferredSize().width;
         if (width > maxWidth)
         {
            maxWidth = width;
         }
      }
      return panel().withContentAnchorX(AX.RIGHT).addAll(buttons, spec().withPreferredWidth(maxWidth)).getComponent();
   }

   @Override
   protected CellSpec getSpec(Orientation parentOrientation)
   {
      return spec().overrideWith(super.getSpec(parentOrientation).withWeightY(0.0));
   }
}
