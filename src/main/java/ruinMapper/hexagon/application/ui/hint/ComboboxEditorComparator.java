package ruinMapper.hexagon.application.ui.hint;

import javafx.scene.control.ComboBox;

public class ComboboxEditorComparator {

    // custom comparator so the items matching the editor text get
    // sorted to the top of the list
    // not perfect but good enough
    public static int compareWithEditorText(String o1,
                                            String o2,
                                            ComboBox<String> editableComboBox) {
        // Lowercasing for easier comparison
        String editText = editableComboBox
                .getEditor()
                .getText().toLowerCase();
        o1 = o1.toLowerCase();
        o2 = o2.toLowerCase();
        // Custom comparator logic
        if (o1.contains(
                editText) && !o2
                .contains(
                        editText)) {
            return -1;
        } else if (!o1
                .contains(
                        editText) && o2
                .contains(
                        editText)) {
            return 1;
        } else {
            return 0;
        }
    }

    public static void sortItemsInDropdown(
            ComboBox<String> editableComboBox) {
        editableComboBox.show();
        editableComboBox
                .getItems()
                .sort((o1, o2) -> compareWithEditorText(
                        o1, o2,
                        editableComboBox));
    }

}
