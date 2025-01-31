import React, { useEffect, useState } from 'react';
import { fetchRebuildTyres } from '../../services/apiDackService';

const RebuildTyrePage = () => {
  const [rebuildTyres, setRebuildTyres] = useState([]);
  const [filteredTyres, setFilteredTyres] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedTyre, setSelectedTyre] = useState(null);

 
  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetchRebuildTyres();
        setRebuildTyres(response.data);
        setFilteredTyres(response.data);
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };
    fetchData();
  }, []);

  //earch by contact number 
  const handleSearch = (e) => {
    const term = e.target.value;
    setSearchTerm(term);
    setFilteredTyres(
      rebuildTyres.filter((tyre) =>
        tyre.contactNumber.includes(term)
      )
    );
  };

  // Open modal for "More Info"
  const handleMoreInfo = (tyre) => {
    setSelectedTyre(tyre);
  };

  // Close modal
  const handleCloseModal = () => {
    setSelectedTyre(null);
  };

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-4">Tyre Rebuilding Section</h1>
      
      {/* Search Input */}
      <div className="mb-4">
        <input
          type="text"
          placeholder="Search by Contact Number"
          value={searchTerm}
          onChange={handleSearch}
          className="p-2 border border-gray-300 rounded w-full"
        />
      </div>

      {/* Table */}
      <div className="overflow-x-auto">
        <table className="w-full border-collapse border border-gray-300">
          <thead className="bg-gray-200">
            <tr>
              <th className="border border-gray-300 p-2">Customer ID</th>
              <th className="border border-gray-300 p-2">Contact Number</th>
              <th className="border border-gray-300 p-2">Date Received</th>
              <th className="border border-gray-300 p-2">Status</th>
              <th className="border border-gray-300 p-2">Bill Number</th>
              <th className="border border-gray-300 p-2">Price</th>
              <th className="border border-gray-300 p-2">Actions</th>
            </tr>
          </thead>
          <tbody>
            {filteredTyres.map((tyre, index) => (
              <tr key={index} className="odd:bg-white even:bg-gray-100">
                <td className="border border-gray-300 p-2">{tyre.customerId}</td>
                <td className="border border-gray-300 p-2">{tyre.contactNumber}</td>
                <td className="border border-gray-300 p-2">{tyre.dateReceived}</td>
                <td className="border border-gray-300 p-2">{tyre.status}</td>
                <td className="border border-gray-300 p-2">{tyre.billNumber}</td>
                <td className="border border-gray-300 p-2">{tyre.price}</td>
                <td className="border border-gray-300 p-2">
                  <button
                    className="bg-blue-500 text-white px-4 py-2 rounded"
                    onClick={() => handleMoreInfo(tyre)}
                  >
                    More Info
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {/* More Info Modal */}
      {selectedTyre && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center">
          <div className="bg-white p-6 rounded shadow-lg w-1/2">
            <h2 className="text-xl font-bold mb-4">Tyre Details</h2>
            <div className="space-y-2">
              <p><strong>Tyre Size:</strong> {selectedTyre.tyreSize}</p>
              <p><strong>Tyre Brand:</strong> {selectedTyre.tyreBrand}</p>
              <p><strong>Tyre Number:</strong> {selectedTyre.tyreNumber}</p>
              <p><strong>Customer Name:</strong> {selectedTyre.customerName}</p>
              <p><strong>Date Sent to Company:</strong> {selectedTyre.dateSentToCompany}</p>
              <p><strong>Sales Rep Number:</strong> {selectedTyre.salesRepNumber}</p>
              <p><strong>Job Number:</strong> {selectedTyre.jobNumber}</p>
              <p><strong>Date Received from Company:</strong> {selectedTyre.dateReceivedFromCompany}</p>
              <p><strong>Date Delivered to Customer:</strong> {selectedTyre.dateDeliveredToCustomer}</p>
            </div>
            <div className="mt-4 text-right">
              <button
                className="bg-gray-500 text-white px-4 py-2 rounded"
                onClick={handleCloseModal}
              >
                Close
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default RebuildTyrePage;
