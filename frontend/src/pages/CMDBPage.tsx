import React, { useState, useEffect } from "react";
import { cmdbService, ConfigurationItem } from "@/services/cmdb.service";
import { AlertCircle, Plus, Trash2 } from "lucide-react";

const CMDBPage: React.FC = () => {
  const [items, setItems] = useState<ConfigurationItem[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [showForm, setShowForm] = useState(false);
  const [formData, setFormData] = useState({
    name: "",
    type: "",
    description: "",
    owner: "",
  });

  useEffect(() => {
    loadItems();
  }, []);

  const loadItems = async () => {
    try {
      setLoading(true);
      const data = await cmdbService.getCIs();
      setItems(data);
      setError(null);
    } catch (err) {
      setError("Failed to load configuration items");
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleCreate = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await cmdbService.createCI(formData);
      setFormData({ name: "", type: "", description: "", owner: "" });
      setShowForm(false);
      await loadItems();
    } catch (err) {
      setError("Failed to create configuration item");
      console.error(err);
    }
  };

  const handleDelete = async (id: string) => {
    if (window.confirm("Are you sure?")) {
      try {
        await cmdbService.deleteCI(id);
        await loadItems();
      } catch (err) {
        setError("Failed to delete configuration item");
        console.error(err);
      }
    }
  };

  return (
    <div className="p-6">
      <div className="mb-6">
        <h1 className="text-3xl font-bold text-secondary-900">CMDB</h1>
        <p className="text-secondary-600 mt-2">Configuration Management Database</p>
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
        Add Configuration Item
      </button>

      {showForm && (
        <form onSubmit={handleCreate} className="mb-6 p-4 bg-white rounded-lg shadow-md">
          <input
            type="text"
            placeholder="Name"
            value={formData.name}
            onChange={(e) => setFormData({ ...formData, name: e.target.value })}
            className="w-full mb-3 p-2 border rounded"
            required
          />
          <input
            type="text"
            placeholder="Type"
            value={formData.type}
            onChange={(e) => setFormData({ ...formData, type: e.target.value })}
            className="w-full mb-3 p-2 border rounded"
            required
          />
          <textarea
            placeholder="Description"
            value={formData.description}
            onChange={(e) => setFormData({ ...formData, description: e.target.value })}
            className="w-full mb-3 p-2 border rounded"
          />
          <input
            type="text"
            placeholder="Owner"
            value={formData.owner}
            onChange={(e) => setFormData({ ...formData, owner: e.target.value })}
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
          {items.map((item) => (
            <div key={item.id} className="p-4 bg-white rounded-lg shadow-md border border-secondary-200">
              <div className="flex justify-between items-start">
                <div className="flex-1">
                  <h3 className="font-semibold text-secondary-900">{item.name}</h3>
                  <p className="text-sm text-secondary-600">{item.type}</p>
                  {item.description && (
                    <p className="text-sm text-secondary-700 mt-2">{item.description}</p>
                  )}
                  <div className="mt-2 flex gap-4 text-xs text-secondary-600">
                    {item.owner && <span>Owner: {item.owner}</span>}
                    <span>Status: {item.status}</span>
                  </div>
                </div>
                <button
                  onClick={() => handleDelete(item.id)}
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

export default CMDBPage;
