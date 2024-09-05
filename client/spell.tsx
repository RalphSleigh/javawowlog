import React from "react";
import { Link, Outlet, useLoaderData, useRouteLoaderData } from "react-router-dom";
import { api } from "./fetcher";
import { Encounter } from "./encounter";


type Spell = {
    timestamp: number,
    amount: number,
}

export const spellLoader = api<Array<Spell>>(p => `/api/encounters/${p.index}/${p.unitId}/${p.spellId}`)

export const Spell = () => {
    const encounterData = useRouteLoaderData("encounter") as Encounter;
    const data = useLoaderData() as Array<Spell>;

    const lines = data.map(d => 
        <tr key={d.timestamp}>
            <td>{d.timestamp}</td>
            <td>{d.amount}</td>
        </tr>
    )

    return <><div>
        <table>
            <thead>
                <tr>
                    <th>Time</th>
                    <th>Damage</th>
                </tr>
            </thead>
            <tbody>
                {lines}
            </tbody>
        </table>
    </div>
        <Outlet />
    </>
}
