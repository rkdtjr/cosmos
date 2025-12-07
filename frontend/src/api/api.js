import axios from 'axios'

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
})

export const getAPOD = async (date) => {
  const res = await api.get(`/apod`)
  return res.data
}
export const getAPODByDate = async (date) => {
  const res = await api.get(`/apod?date=${date}`)
  return res.data
}
export const getGallerySearch = async (keyword, page) => {
  const res = await api.get(`/gallery/search?keyword=${keyword}&page=${page}`)
  return res.data
}
export const getDictionary = async () => {
  const res = await api.get(`/dictionary`)
  return res.data
}
export const getDictionaryDetail = async (id) => {
  const res = await api.get(`/dictionary/${id}`)
  return res.data
}