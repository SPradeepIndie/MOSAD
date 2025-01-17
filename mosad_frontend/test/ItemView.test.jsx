import React from "react";
import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import { MemoryRouter } from "react-router-dom";
import ItemView from "../src/pages/stock_section/ItemView";
import { fetchItems, addItem } from "../src/services/apiStockService";
import { describe, test, beforeEach, vi } from "vitest";


// Mock services
vi.mock("../src/services/apiStockService");

describe("ItemView Component", () => {
  const mockLocation = {
    state: { category: "Tyre", brand: "RAPID" },
  };

  beforeEach(() => {
    vi.clearAllMocks();
  });

  test("renders the banner image based on the brand", () => {
    render(
      <MemoryRouter>
        <ItemView />
      </MemoryRouter>
    );

    const bannerImage = screen.getByAltText("Brand Banner");
    expect(bannerImage).toBeInTheDocument();
    expect(bannerImage).toHaveAttribute("src", expect.stringContaining("rapid.jpg"));
  });

  test("fetches and displays items on mount", async () => {
    const mockItems = [
      {
        itemID: 1,
        pattern: "Pattern1",
        tyreSize: "Size1",
        ply: 4,
        officialSellingPrice: 100.5,
        tyreCount: 20,
      },
    ];
    fetchItems.mockResolvedValue({ data: mockItems });
    render(
      <MemoryRouter initialEntries={[{ pathname: "/", state: mockLocation.state }]}>
        <ItemView selectedCategory="Tyre" selectedBrand="RAPID" />
      </MemoryRouter>
    );
    await waitFor(() => {
      expect(fetchItems).toHaveBeenCalledWith({
        params: { category: "Tyre", brand: "RAPID" },
      });
      const row = screen.getByText("Pattern1");
      expect(row).toBeInTheDocument();
    });
  });
  
  test("opens the dialog for adding a new item", () => {
    render(
      <MemoryRouter>
        <ItemView selectedCategory="Tyre" selectedBrand="RAPID" />
      </MemoryRouter>
    );
    const addButton = screen.getByText("Add Item");
    fireEvent.click(addButton);
    const dialogTitle = screen.getByText("Add New Item");
    expect(dialogTitle).toBeInTheDocument();
  });
  test("validates and displays errors on form submit", async () => {
    render(
      <MemoryRouter>
        <ItemView selectedCategory="Tyre" selectedBrand="RAPID" />
      </MemoryRouter>
    );
    const addButton = screen.getByText("Add Item");
    fireEvent.click(addButton);
    const submitButton = screen.getByText("Submit");
    fireEvent.click(submitButton);
    await waitFor(() => {
      const errorMessage = screen.getByText("Please re-check all red fields!");
      expect(errorMessage).toBeInTheDocument();
    });
  });
  test("calls addItem API on form submit with valid data", async () => {
    addItem.mockResolvedValue({});
    render(
      <MemoryRouter>
        <ItemView selectedCategory="Tyre" selectedBrand="RAPID" />
      </MemoryRouter>
    );
    const addButton = screen.getByText("Add Item");
    fireEvent.click(addButton);
    // Simulate form filling
    const inputs = screen.getAllByRole("textbox");
    fireEvent.change(inputs[0], { target: { value: "Pattern A" } });
    fireEvent.change(inputs[1], { target: { value: "Size A" } });
    fireEvent.change(inputs[2], { target: { value: 4 } });
    fireEvent.change(inputs[3], { target: { value: 150.5 } });

    const submitButton = screen.getByText("Submit");
    fireEvent.click(submitButton);

    await waitFor(() => {
      expect(addItem).toHaveBeenCalledWith(
        "tyre_rapid",
        expect.objectContaining({
          pattern: "Pattern A",
          tyreSize: "Size A",
          ply: 4,
          officialSellingPrice: 150.5,
        })
      );
    });
  });
 });

