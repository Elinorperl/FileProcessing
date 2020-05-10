package filesprocessing;

/**
 * A class that is in charge of ensuring that section is intact.
 */
public class Section {
    String filter;
    String order;
    String[] filterDescription;
    String[] orderDescription;
    int line;
    public Section(String[] list) {
        filter = list[1];
        order = list[2];
        line = Integer.parseInt(list[0]);
        filterDescription = splitFilter(filter);
        orderDescription = splitOrder(order);
    }

    /**
     * Splits the filter so that we can work with it.
     *@param filterString - the string for which the function splits into a list.
     *@return a list of strings which describe the filter.
     */
    private String[] splitFilter(String filterString) {
        String [] filterType;
            filterType = filterString.split("#");
            return filterType;
    }

    /**
     * Splits the order so that we can work with it.
     *@param orderString - the string for which the function splits into a list.
     *@return a list of strings, for the order, and its description.
     */
    private String[] splitOrder(String orderString) {
        String[] orderType = orderString.split("#");
        return orderType;
    }

    /**
     * A getter function for the spilt order.
     *@return a list of strings of the orders description.
     */
    protected String[] getSplitOrder() {
        return orderDescription;
    }

    /**
     * A getter function for the spilt filter.
     *@return a list of strings of the filter's description.
     */
    protected String[] getSplitFilter() {
        return filterDescription;
    }


}
