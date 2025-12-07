import { useQuery } from "@tanstack/react-query";
import { getGallerySearch } from "../api/api";
import { useState } from "react";
import Tag from "../components/Tag";
import ImageCard from "../components/ImageCard";
import ImageDetail from "../components/ImageDetail";

function Gallery() {
  const [keyword, setKeyword] = useState("");
  const [query, setQuery] = useState("");
  const [page, setPage] = useState(1);       // 20개씩 보여줄 프론트 페이지
  const [nasaPage, setNasaPage] = useState(1); // NASA API page
  const [selectedImage, setSelectedImage] = useState(null);

  const { data, isLoading, isError, refetch } = useQuery({
    queryKey: ["gallery", query, nasaPage],
    queryFn: () => getGallerySearch(query, nasaPage),
    refetchOnWindowFocus: false,
  });

  const handleSearch = () => {
    setPage(1);
    setNasaPage(1);
    setQuery(keyword);
  };

  if (isLoading) return <p className="text-center mt-10">Loading...</p>;
  if (isError) return <p className="text-center mt-10">오류 발생</p>;

  const visibleImages = data?.slice(0, page * 20) ?? [];

  return (
    <div>
      {/* 검색 */}
      <div className="w-full flex justify-center mb-10 mt-4">
        <div className="flex gap-3 w-full max-w-[500px]">
          <input
            type="text"
            placeholder="Search space images..."
            className="flex-1 px-4 py-2 rounded-lg bg-[#0d1b2a] border border-gray-600 text-white"
            value={keyword}
            onChange={(e) => setKeyword(e.target.value)}
            onKeyDown={(e) => e.key === "Enter" && handleSearch()}
          />
          <button
            onClick={handleSearch}
            className="px-4 py-2 bg-cyan-600 text-white rounded-lg"
          >
            검색
          </button>
        </div>
      </div>

      {/* 이미지 목록 */}
      <div className="columns-1 sm:columns-2 md:columns-3 lg:columns-4 xl:columns-5 gap-6 px-6 mx-auto max-w-[1400px]">
        {visibleImages.map((image) => (
          <div
            key={image.nasaId}
            className="mb-6 break-inside-avoid rounded-xl bg-[#0d1b2a] shadow-md overflow-hidden cursor-pointer"
            onClick={() => setSelectedImage(image)}
          >
            <ImageCard image={image} />
            <div className="flex gap-2 flex-wrap justify-center px-2 pb-3 mt-2">
              {image.tags?.map((tag) => (
                <Tag key={tag} tag={tag} />
              ))}
            </div>
          </div>
        ))}
      </div>

      {/* 버튼 */}
      <div className="text-center mt-8 flex gap-3 justify-center">
        {/* 더보기 */}
        {visibleImages.length < data?.length && (
          <button
            onClick={() => setPage((p) => p + 1)}
            className="px-6 py-3 bg-cyan-600 text-white rounded-lg font-semibold"
          >
            더보기
          </button>
        )}

        {query !== "" && visibleImages.length >= data?.length && (
          <button
            onClick={() => {
              setNasaPage((prev) => prev + 1);
              setPage(1); // 처음 20개부터 보여줌
            }}
            className="px-6 py-3 bg-purple-600 text-white rounded-lg font-semibold"
          >
            다음 페이지 ➜
          </button>
        )}

      </div>

      <ImageDetail image={selectedImage} onClose={() => setSelectedImage(null)} />
    </div>
  );
}

export default Gallery;
