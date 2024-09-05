import React from "react";
import { Link, Outlet, useLoaderData } from "react-router-dom";
import { api } from "./fetcher";
import { Table } from "@mantine/core";


type Unit = {
    id: string,
    name: string,
    friendly: boolean
}

type Spell = {
    id: string,
    name: string,
}

export type Encounter = {
    name: string,
    units: Record<string, Unit>,
    spells: Record<string, Spell>,
    damageDone: Record<string, number>,
}

export const encounterLoader = api<Encounter>(p => `/api/encounters/${p.index}`)

export const Encounter = () => {
    const data = useLoaderData() as Encounter;

    const lines = Object.entries(data.damageDone)
    .filter(([unitId, damage]) => data.units[unitId].friendly)
    .sort((a, b)=> b[1] - a[1]).map(([unitId, damage]) => {
        return <Table.Tr key={unitId}><Table.Td><Link to={unitId}>{data.units[unitId].name}</Link></Table.Td><Table.Td>{damage}</Table.Td></Table.Tr>
    })

    return <>
    <Table>
      <Table.Thead>
        <Table.Tr>
          <Table.Th>Unit</Table.Th>
          <Table.Th>Damage</Table.Th>
        </Table.Tr>
      </Table.Thead>
      <Table.Tbody>{lines}</Table.Tbody>
    </Table>
    <Outlet />
    </>
}
