package base.domain;

import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author who
 */
public class KendoTreeNode {
    // FIXME
    // Serializable  ???????
    private Serializable id;
    private String text;
    private Boolean checked;

    public KendoTreeNode(Serializable id, String text) {
        this.id = id;
        this.text = text;
    }

    public static <T extends Nameable> List<KendoTreeNode> toTreeNodes(final List<T> values, final List<T> checkedValues) {
        return values.stream()
                .map(value -> {
                    KendoTreeNode node = new KendoTreeNode((Serializable) value.getId(), value.getName());
                    boolean contains = checkedValues.stream()
                            .anyMatch(p -> p.getId().equals(node.getId()));
                    if (contains) {
                        node.setChecked(true);
                    }
                    return node;
                })
                .collect(toList());
    }


    public Serializable getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Boolean isChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("text", text)
                .add("checked", checked)
                .toString();
    }

}
