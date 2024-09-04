import { useParams } from "react-router"

type LoaderFunction<T> = (params: any) => Promise<T>

export const api = <T>(path: (params: any) => string): LoaderFunction<T> => {
    return async params => {
    const response = await fetch(path(params.params))
    return await response.json() as T
    }
}