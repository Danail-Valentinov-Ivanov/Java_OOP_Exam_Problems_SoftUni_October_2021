package shopAndGoods;


import org.junit.Before;
import org.junit.Test;

import javax.naming.OperationNotSupportedException;

import static org.junit.Assert.assertEquals;

public class ShopTest {
    // TODO
    private Shop shop;
    private Goods goods;

    @Before
    public void setUp() throws Exception {
        this.shop = new Shop();
        this.goods = new Goods("testGoodName", "testGoodCode");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetShelvesShouldReturnUnmodifiableCollection() {
        shop.getShelves().clear();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddGoodsShouldFailForInvalidShelve() throws OperationNotSupportedException {
        shop.addGoods("invalidShelf", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddGoodsShouldFailForExistingGood() throws OperationNotSupportedException {
        shop.addGoods("Shelves1", this.goods);
        shop.addGoods("Shelves1", this.goods);
    }

    @Test
    public void testAddGoodsShouldReturnCorrectMessageOnAddition() throws OperationNotSupportedException {
        String expected = "Goods: testGoodCode is placed successfully!";
        String actual = shop.addGoods("Shelves1", goods);
        assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveGoodsShouldFailForInvalidShelve() {
        shop.removeGoods("invalidShelf", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveGoodsShouldFailForDifferentGoodsOnSameShelve() throws OperationNotSupportedException {
        Goods otherGoods = new Goods("testGoodNameOther", "testGoodCodeOther");
        shop.addGoods("Shelves1", goods);
        shop.removeGoods("Shelves1", otherGoods);
    }

    @Test
    public void testRemoveGoodsShouldReturnCorrectMessage() throws OperationNotSupportedException {
        shop.addGoods("Shelves1", goods);
        String expected = "Goods: testGoodCode is removed successfully!";
        String actual = shop.removeGoods("Shelves1", goods);
        assertEquals(expected, actual);
    }

    @Test
    public void testRemoveGoodsShouldSetShelveValueToNull() throws OperationNotSupportedException {
        shop.addGoods("Shelves1", goods);
        shop.removeGoods("Shelves1", goods);
        Goods actualGoods = shop.getShelves().get("Shelves1");
        assertEquals(null, actualGoods);
    }
}