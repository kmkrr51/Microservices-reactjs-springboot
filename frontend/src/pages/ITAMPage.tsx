import React, { useState, useEffect } from "react";
import { itamService, HardwareAsset } from "@/services/itam.service";
import { AlertCircle, Plus, Trash2 } from "lucide-react";

const ITAMPage: React.FC = () => {
  const [assets, setAssets] = useState<HardwareAsset[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [showForm, setShowForm] = useState(false);
  const [formData, setFormData] = useState({
    assetTag: "",
    name: "",
    assetType: "",
    manufacturer: "",
    model: "",
    serialNumber: "",
    purchaseCost: "",
  });

  useEffect(() => {
    loadAssets();
  }, []);

  const loadAssets = async () => {
    try {
      setLoading(true);
      const data = await itamService.getAssets();
      setAssets(data);
      setError(null);
    } catch (err) {
      setError("Failed to load assets");
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleCreate = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await itamService.createAsset({
        ...formData,
        purchaseCost: formData.purchaseCost ? parseFloat(formData.purchaseCost) : undefined,
      });
      setFormData({
        assetTag: "",
        name: "",
        assetType: "",
        manufacturer: "",
        model: "",
        serialNumber: "",
        purchaseCost: "",
      });
      setShowForm(false);
      await loadAssets();
    } catch (err) {
      setError("Failed to create asset");
      console.error(err);
    }
  };

  const handleDelete = async (id: string) => {
    if (window.confirm("Are you sure?")) {
      try {
        await itamService.deleteAsset(id);
        await loadAssets();
      } catch (err) {
        setError("Failed to delete asset");
        console.error(err);
      }
    }
  };

  return (
    <div className="p-6">
      <div className="mb-6">
        <h1 className="text-3xl font-bold text-secondary-900">ITAM</h1>
        <p className="text-secondary-600 mt-2">IT Asset Management - Hardware Assets</p>
      </div>

      {error && (
        <div className="mb-4 p-4 bg-red-50 border border-red-200 rounded-lg flex items-center gap-2">
          <AlertCircle className="w-5 h-5 text-red-600" />
          <span className="text-red-700">{error}</span>
        </div>
      )}

      <button
        onClick={() => setShowForm(!showForm)}
        className="mb-4 px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 flex items-center gap-2"
      >
        <Plus className="w-4 h-4" />
        Add Hardware Asset
      </button>

      {showForm && (
        <form onSubmit={handleCreate} className="mb-6 p-4 bg-white rounded-lg shadow-md">
          <input
            type="text"
            placeholder="Asset Tag"
            value={formData.assetTag}
            onChange={(e) => setFormData({ ...formData, assetTag: e.target.value })}
            className="w-full mb-3 p-2 border rounded"
            required
          />
          <input
            type="text"
            placeholder="Asset Name"
            value={formData.name}
            onChange={(e) => setFormData({ ...formData, name: e.target.value })}
            className="w-full mb-3 p-2 border rounded"
            required
          />
          <input
            type="text"
            placeholder="Asset Type"
            value={formData.assetType}
            onChange={(e) => setFormData({ ...formData, assetType: e.target.value })}
            className="w-full mb-3 p-2 border rounded"
            required
          />
          <input
            type="text"
            placeholder="Manufacturer"
            value={formData.manufacturer}
            onChange={(e) => setFormData({ ...formData, manufacturer: e.target.value })}
            className="w-full mb-3 p-2 border rounded"
          />
          <input
            type="text"
            placeholder="Model"
            value={formData.model}
            onChange={(e) => setFormData({ ...formData, model: e.target.value })}
            className="w-full mb-3 p-2 border rounded"
          />
          <input
            type="text"
            placeholder="Serial Number"
            value={formData.serialNumber}
            onChange={(e) => setFormData({ ...formData, serialNumber: e.target.value })}
            className="w-full mb-3 p-2 border rounded"
          />
          <input
            type="number"
            placeholder="Purchase Cost"
            value={formData.purchaseCost}
            onChange={(e) => setFormData({ ...formData, purchaseCost: e.target.value })}
            className="w-full mb-3 p-2 border rounded"
          />
          <button
            type="submit"
            className="px-4 py-2 bg-green-600 text-white rounded-lg hover:bg-green-700"
          >
            Create
          </button>
        </form>
      )}

      {loading ? (
        <div className="text-center py-8">Loading...</div>
      ) : (
        <div className="grid gap-4">
          {assets.map((asset) => (
            <div key={asset.id} className="p-4 bg-white rounded-lg shadow-md border border-secondary-200">
              <div className="flex justify-between items-start">
                <div className="flex-1">
                  <h3 className="font-semibold text-secondary-900">{asset.name}</h3>
                  <p className="text-sm text-secondary-600">{asset.assetTag}</p>
                  <div className="mt-2 grid grid-cols-2 gap-2 text-xs text-secondary-600">
                    {asset.manufacturer && <span>Mfg: {asset.manufacturer}</span>}
                    {asset.model && <span>Model: {asset.model}</span>}
                    {asset.serialNumber && <span>Serial: {asset.serialNumber}</span>}
                    <span>Status: {asset.status}</span>
                    {asset.assignedTo && <span>Assigned: {asset.assignedTo}</span>}
                  </div>
                </div>
                <button
                  onClick={() => handleDelete(asset.id)}
                  className="p-2 text-red-600 hover:bg-red-50 rounded"
                >
                  <Trash2 className="w-4 h-4" />
                </button>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default ITAMPage;
