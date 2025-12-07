import { useState } from "react";
import { useQuery } from "@tanstack/react-query";
import { getDictionary, getDictionaryDetail } from "../api/api";


export default function Dictionary() {
  const [selectedId, setSelectedId] = useState(null);

  const { data: list, isLoading: listLoading } = useQuery({
    queryKey: ["dictionaryList"],
    queryFn: () => getDictionary(),
  });

  const { data: detail, isLoading: detailLoading } = useQuery({
    queryKey: ["dictionaryDetail", selectedId],
    queryFn: () => getDictionaryDetail(selectedId),
    enabled: !!selectedId,
  });

  if (listLoading) return <p className="text-center mt-10">Loading...</p>;

  return (
    <div className="flex h-screen bg-[#060B17] text-white">

      <div className="w-1/3 overflow-y-auto border-r border-gray-700">
        {list?.map(item => (
          <div
            key={item.id}
            onClick={() => setSelectedId(item.id)}
            className={`p-4 cursor-pointer hover:bg-gray-700 ${selectedId === item.id ? "bg-gray-700" : ""
              }`}
          >
            <h3 className="text-xl font-bold">{item.name}</h3>
            <p className="text-sm text-gray-400">{item.type}</p>
          </div>
        ))}
      </div>

      <div className="w-2/3 p-6">
        {detailLoading && <p>Loading detail...</p>}
        {!detail && !detailLoading && <p>항목을 선택하세요.</p>}

        {detail && (
          <>
            <h2 className="text-3xl font-bold mb-2">{detail.name}</h2>
            <p className="text-gray-300 mb-4">{detail.description}</p>

            <div className="flex flex-wrap gap-2 mb-4">
              {detail.relatedTags?.map(tag => (
                <span key={tag} className="px-2 py-1 bg-blue-600 rounded-md text-sm">
                  #{tag}
                </span>
              ))}
            </div>

            {detail.imageRef && (
              <img
                src={detail.imageRef}
                alt={detail.name}
                className="rounded-lg max-h-[300px] object-contain"
              />
            )}

            <p className="text-gray-500 text-sm mt-3">
              Created At: {detail.createdAt}
            </p>
          </>
        )}
      </div>

    </div>
  );
}
