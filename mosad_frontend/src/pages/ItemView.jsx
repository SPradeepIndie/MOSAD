import React, { useState, useEffect } from "react";
import "./ItemView.css";

const ItemView = ({ selectedCategory, selectedBrand }) => {
  const [rows, setRows] = useState([]);
  const [selectedRowId, setSelectedRowId] = useState(null);
  const [bannerImage, setBannerImage] = useState("");

  // Fetch table data from backend
  useEffect(() => {
    if (selectedCategory && selectedBrand) {
      fetch(
        `http://localhost:8080/api/items?category=${selectedCategory}&brand=${selectedBrand}`
      )
        .then((response) => response.json())
        .then((data) => setRows(data))
        .catch((error) => console.error("Error fetching data:", error));
    }
  }, [selectedCategory, selectedBrand]);

  // Update banner image based on brand
  useEffect(() => {
    const brandImages = {
      atlander: "/assets/atlander.png",
      presa: "/assets/presa.png",
      dsi: "/assets/dsi.png",
      linglong: "/assets/linglong.png",
      rapid: "/assets/rapid.png"
    };
    setBannerImage(brandImages[selectedBrand] || "/assets/default.png");
  }, [selectedBrand]);

  // Handle row selection
  const handleRowClick = (id) => setSelectedRowId(id);

  return (
    <div className="item-view-container">
      {/* Banner */}
      <section className="banner">
        <img src={bannerImage} alt="Brand Banner" className="brand-banner" />
      </section>

      {/* Table */}
      <table className="item-table">
        <thead>
          <tr>
            <th>Pattern</th>
            <th>Size</th>
            <th>PR</th>
            <th>Selling Price</th>
            <th>Stock</th>
          </tr>
        </thead>
        <tbody>
          {rows.map((row) => (
            <tr
              key={row.id}
              className={selectedRowId === row.id ? "selected-row" : ""}
              onClick={() => handleRowClick(row.id)}
            >
              <td>{row.pattern}</td>
              <td>{row.size}</td>
              <td>{row.pr}</td>
              <td>{row.price}</td>
              <td>{row.stock}</td>
            </tr>
          ))}
        </tbody>
      </table>
      
      <div className="button-group">
        <button className="btn delete">Delete</button>
        <button className="btn update">Update</button>
        <button className="btn add">Add Item</button>
        <button className="btn info">More Info</button>
      </div>    

    </div>

  );
};

export default ItemView;
